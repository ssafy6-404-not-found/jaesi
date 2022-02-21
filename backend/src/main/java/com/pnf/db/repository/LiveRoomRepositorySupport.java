package com.pnf.db.repository;

import com.pnf.db.entity.LiveRoom;
import com.pnf.db.entity.QLiveRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LiveRoomRepositorySupport{
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QLiveRoom qLiveRoom = QLiveRoom.liveRoom;


    public List<LiveRoom> findAll() {
        List<LiveRoom> list= jpaQueryFactory.selectFrom(qLiveRoom).where(qLiveRoom.endTime.isNull()).fetch();
        return list;
    }

    public LiveRoom findOneOrderByRealTimeViewersDesc() {
        LiveRoom liveRoom = jpaQueryFactory.selectFrom(qLiveRoom).where(qLiveRoom.endTime.isNull()).orderBy(qLiveRoom.realTimeViewers.desc()).fetchFirst();
        return liveRoom;
    }
}
