package com.pnf.api.repository;

import com.pnf.api.request.LiveRoomStartPostReq;
import com.pnf.db.entity.LiveRoom;
import com.pnf.db.entity.Product;
import com.pnf.db.repository.LiveRoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveRepositoryTest {

    @Autowired
    LiveRoomRepository repository;

    @Test
    public void 라이브_시작() {
        LiveRoomStartPostReq liveRoomStartPostReq = new LiveRoomStartPostReq();
        liveRoomStartPostReq.setHostId(1L);
        liveRoomStartPostReq.setCategoryId(1L);
        liveRoomStartPostReq.setTitle("테스트");
        liveRoomStartPostReq.setDescription("좋은 방송");

        LiveRoom liveRoom = repository.findByUserIdAndEndTimeIsNull(1L);
        System.out.println(liveRoom.toString());
    }

}
