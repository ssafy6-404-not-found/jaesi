package com.pnf.api.controller;

import com.pnf.api.request.ChatRoomStartPostReq;
import com.pnf.api.response.ChatRoomGetRes;
import com.pnf.api.response.ChatRoomPostRes;
import com.pnf.api.service.ChatRoomService;
import com.pnf.db.entity.ChatRoom;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "채팅", tags = {"ChatRoom"})
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    ChatRoomService chatRoomService;

    @GetMapping("/{userId}")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "사용자의 채팅 목록 조회 - jwt", notes = "사용자의 채팅 목록을 조회한다.")
    public ResponseEntity<List<ChatRoomGetRes>> listChatRoom(@PathVariable("userId")long userId) {
        return new ResponseEntity(chatRoomService.getListChatRoom(userId), HttpStatus.OK);
    }

    @PostMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "채팅 시작 요청 - jwt", notes = " receive유저와 send유저 서로 바꿔서 보내도 동일한 요청으로 처리함(중복 제거)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 402, message = "유저가 없거나 이미 두 유저간의 채팅방이 존재함"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<ChatRoomPostRes> startChat(@RequestBody ChatRoomStartPostReq chatRoomStartPostReq) {

        ChatRoom chatRoom;
        if ((chatRoom = chatRoomService.startChat(chatRoomStartPostReq)) != null) {
            return ResponseEntity.status(200).body(ChatRoomPostRes.of(200, "Success",chatRoom.getId()));
        } else {
            return ResponseEntity.status(400).body(ChatRoomPostRes.of(402, "Fail - chat is already exists or user not found",null));
        }
    }

}
