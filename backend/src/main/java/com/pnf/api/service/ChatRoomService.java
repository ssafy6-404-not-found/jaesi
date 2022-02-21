package com.pnf.api.service;

import com.pnf.api.request.ChatRoomStartPostReq;
import com.pnf.api.response.ChatRoomGetRes;
import com.pnf.db.entity.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomGetRes> getListChatRoom(long userId);
    ChatRoom startChat(ChatRoomStartPostReq chatRoomStartPostReq);
}
