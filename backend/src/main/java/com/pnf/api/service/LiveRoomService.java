package com.pnf.api.service;

import com.pnf.api.request.*;
import com.pnf.api.response.LiveRoomGetRes;
import com.pnf.db.entity.LiveRoom;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface LiveRoomService {
    Page<LiveRoomGetRes> getListLiveRoom(LiveRoomListGetReq liveRoomListGetReq);
    LiveRoom startLive (LiveRoomStartPostReq liveRoomStartPostReq) throws IOException;
    boolean endLive(LiveRoomEndReq liveRoomEndReq);
    LiveRoomGetRes selectHotLive();
    boolean increaseHit(LiveRoomIncreaseHitReq liveRoomIncreaseHitReq);
    boolean updateLive(LiveRoomUpdateReq liveRoomUpdateReq);
}
