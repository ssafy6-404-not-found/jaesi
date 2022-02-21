package com.pnf.api.service;

import com.pnf.api.request.LiveRoomIncreaseHitReq;
import com.pnf.api.request.LiveRoomStartPostReq;
import com.pnf.api.request.LiveRoomUpdateReq;
import com.pnf.db.entity.LiveRoom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiveRoomServiceTest {
    @Autowired
    LiveRoomService service;

    @Test
    public void 라이브_시작() throws IOException {
        LiveRoomStartPostReq liveRoomStartPostReq = new LiveRoomStartPostReq();
        liveRoomStartPostReq.setHostId(1L);
        liveRoomStartPostReq.setCategoryId(1L);
        liveRoomStartPostReq.setTitle("테스트");
        liveRoomStartPostReq.setDescription("좋은 방송");

        LiveRoom liveRoom = service.startLive(liveRoomStartPostReq);
        System.out.println(liveRoom);
    }

    @Test
    public void 라이브_hit증가() throws IOException {
        LiveRoomIncreaseHitReq liveRoomStartPostReq = new LiveRoomIncreaseHitReq();
        liveRoomStartPostReq.setHostId(1L);

        boolean liveRoom = service.increaseHit(liveRoomStartPostReq);
        System.out.println(liveRoom);
    }

    @Test
    public void 라이브_업데이트() throws IOException {
        LiveRoomUpdateReq liveRoomUpdateReq = new LiveRoomUpdateReq();
        liveRoomUpdateReq.setHostId(1L);
        liveRoomUpdateReq.setRealTimeViewers(10L);

        boolean isSucess = service.updateLive(liveRoomUpdateReq);
        System.out.println(isSucess);
    }
}
