package com.pnf.api.controller;

import com.pnf.api.request.*;
import com.pnf.api.response.LiveRoomPostRes;
import com.pnf.api.response.LiveRoomGetRes;
import com.pnf.api.service.LiveRoomService;
import com.pnf.api.response.BaseResponseBody;
import com.pnf.db.entity.LiveRoom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Api(value = "라이브", tags = {"LiveRoom"})
@RestController
@RequestMapping("/live")
public class LiveRoomController {

    @Autowired
    LiveRoomService liveRoomService;

    @GetMapping("/list")
    @ApiOperation(value = "라이브 목록 조회", notes = "라이브 목록을 조회한다.")
    public ResponseEntity<Page<LiveRoomGetRes>> listLiveRoom(@ApiParam(value = "목록 페이징에 필요한 부가정보", required = true) LiveRoomListGetReq liveRoomListGetReq) {
        return new ResponseEntity<Page<LiveRoomGetRes>>(liveRoomService.getListLiveRoom(liveRoomListGetReq), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "라이브 시작 - jwt", notes = "호스트가 이전에 종료하지 않은 방송들이 있으면 모두 종료하고 라이브를 시작한다.")
    public ResponseEntity<? extends LiveRoomPostRes> startLive(@RequestBody LiveRoomStartPostReq liveRoomStartPostReq) {
        try {
            LiveRoom liveRoom;
            if ((liveRoom = liveRoomService.startLive(liveRoomStartPostReq)) != null) {
                return ResponseEntity.status(200).body(LiveRoomPostRes.of(200, "Success",liveRoom.getId()));
            } else {
                return ResponseEntity.status(400).body(LiveRoomPostRes.of(400, "Fail",null));
            }
        } catch (IOException e) {
            return ResponseEntity.status(400).body(LiveRoomPostRes.of(500, "Inter error",null));
        }
    }

    @DeleteMapping("")
    @ApiOperation(value = "라이브 종료")
    public ResponseEntity<? extends BaseResponseBody> endLive(@RequestBody LiveRoomEndReq liveRoomEndReq) {

        if (liveRoomService.endLive(liveRoomEndReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Fail"));
        }
    }

    @GetMapping("/hot")
    @ApiOperation(value = "핫한 라이브 조회")
    public ResponseEntity<LiveRoomGetRes> listHotLive() {
        LiveRoomGetRes liveRoomGetRes = liveRoomService.selectHotLive();
        return new ResponseEntity<LiveRoomGetRes>(liveRoomGetRes, HttpStatus.OK);
    }

    @PutMapping("/hit")
    @ApiOperation(value = "hit수 증가")
    public ResponseEntity<? extends BaseResponseBody> increaseHit(@RequestBody LiveRoomIncreaseHitReq liveRoomIncreaseHitReq) {

        if(liveRoomService.increaseHit(liveRoomIncreaseHitReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,"Fail"));
        }
    }

    @PutMapping("/update")
    @ApiOperation(value = "썸네일, 시청자수 업데이트")
    public ResponseEntity<? extends BaseResponseBody> updateLive(@RequestBody LiveRoomUpdateReq liveRoomUpdateReq) {

        if(liveRoomService.updateLive(liveRoomUpdateReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,"Fail"));
        }
    }

}
