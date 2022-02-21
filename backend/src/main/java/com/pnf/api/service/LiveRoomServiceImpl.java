package com.pnf.api.service;

import com.pnf.api.request.*;
import com.pnf.api.response.LiveRoomGetRes;
import com.pnf.db.entity.Category;
import com.pnf.db.entity.LiveRoom;
import com.pnf.db.entity.User;
import com.pnf.db.repository.CategoryRepository;
import com.pnf.db.repository.LiveRoomRepository;
import com.pnf.db.repository.LiveRoomRepositorySupport;
import com.pnf.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("liveRoomService")
public class LiveRoomServiceImpl implements LiveRoomService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    LiveRoomRepository liveRoomRepository;

    @Autowired
    LiveRoomRepositorySupport liveRoomRepositorySupport;

    @Override
    public Page<LiveRoomGetRes> getListLiveRoom(LiveRoomListGetReq liveRoomListGetReq) {

        Sort sort = (liveRoomListGetReq.getSortType() == 0)? Sort.by("startTime").descending().and(Sort.by("hit").descending()):Sort.by("hit").descending().and(Sort.by("startTime").descending());
        Pageable pageable = PageRequest.of(liveRoomListGetReq.getCurrPage()-1, liveRoomListGetReq.getPerPage(), sort);
        Page<LiveRoomGetRes> liveRoomGetRes;

        if (liveRoomListGetReq.getCategory() == 0) //전체 목록
            liveRoomGetRes = liveRoomRepository.findByEndTimeIsNull(pageable).map(LiveRoomGetRes::of);
        else
            liveRoomGetRes = liveRoomRepository.findByCategoryIdAndEndTimeIsNull(Long.valueOf(liveRoomListGetReq.getCategory()),pageable).map(LiveRoomGetRes::of);

        return liveRoomGetRes;
    }

    @Override
    public LiveRoom startLive(LiveRoomStartPostReq liveRoomStartPostReq) {

        List<LiveRoom> liveRooms = liveRoomRepository.findAllByUserIdAndEndTimeIsNull(liveRoomStartPostReq.getHostId());
        if (liveRooms != null) { //유저의 종료되지 않은 방송이 있으면 종료
            for (LiveRoom l : liveRooms) {
                l.setEndTime(LocalDateTime.now());
                liveRoomRepository.save(l);
            }
        }
        Optional<User> optionalUser = userRepository.findById(liveRoomStartPostReq.getHostId());
        User user;
        if (optionalUser.isPresent())
            user = optionalUser.get();
        else    //유저가 없으면
            return null;

        Category category = categoryRepository.findById(liveRoomStartPostReq.getCategoryId()).get();

        LiveRoom liveRoom = LiveRoom.builder()
                .user(user)
                .category(category)
                .password(liveRoomStartPostReq.getPassword())
                .title(liveRoomStartPostReq.getTitle())
                .description(liveRoomStartPostReq.getDescription())
                .build();

        liveRoomRepository.save(liveRoom);

        return liveRoom;
    }

    @Override
    public boolean endLive(LiveRoomEndReq liveRoomEndReq) {
        LiveRoom liveRoom = liveRoomRepository.findById(liveRoomEndReq.getLiveRoomId()).get();
        liveRoom.endLive(LocalDateTime.now());
        liveRoomRepository.save(liveRoom);
        return true;
    }

    @Override
    public LiveRoomGetRes selectHotLive() {
        LiveRoom liveRoom = liveRoomRepositorySupport.findOneOrderByRealTimeViewersDesc();
        if (liveRoom == null)
            return new LiveRoomGetRes();

        LiveRoomGetRes liveRoomGetRes = LiveRoomGetRes.builder()
                .id(liveRoom.getId())
                .hostId(liveRoom.getUser().getId())
                .hostNickname((liveRoom.getUser().getNickname()))
                .title(liveRoom.getTitle())
                .description(liveRoom.getDescription())
                .thumbnail(liveRoom.getThumbnail())
                .startTime(liveRoom.getStartTime())
                .hit(liveRoom.getHit())
                .realTimeViewers(liveRoom.getRealTimeViewers()).build();
        return liveRoomGetRes;
    }

    @Override
    public boolean increaseHit(LiveRoomIncreaseHitReq liveRoomIncreaseHitReq) {

        LiveRoom liveRoom = liveRoomRepository.findByUserIdAndEndTimeIsNull(liveRoomIncreaseHitReq.getHostId());

        liveRoom.increaseHit(liveRoom.getHit());

        liveRoomRepository.save(liveRoom);

        return true;
    }

    @Override
    public boolean updateLive(LiveRoomUpdateReq liveRoomUpdateReq) {

        LiveRoom liveRoom = liveRoomRepository.findByUserIdAndEndTimeIsNull(liveRoomUpdateReq.getHostId());
        if (liveRoom == null)   //라이브방이 없으면 실패
            return false;

        liveRoom.setThumbnail((liveRoomUpdateReq.getThumbnail() != null)? liveRoomUpdateReq.getThumbnail() : liveRoom.getThumbnail());
        liveRoom.setRealTimeViewers((liveRoomUpdateReq.getRealTimeViewers() != null)? liveRoomUpdateReq.getRealTimeViewers() : liveRoom.getRealTimeViewers());

        liveRoomRepository.save(liveRoom);
        return true;
    }
}
