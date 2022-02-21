package com.pnf.api.service;

import com.pnf.api.request.ChatRoomStartPostReq;
import com.pnf.api.response.ChatRoomGetRes;
import com.pnf.db.entity.ChatRoom;
import com.pnf.db.entity.User;
import com.pnf.db.repository.ChatRoomRepository;
import com.pnf.db.repository.ChatRoomRepositorySupport;
import com.pnf.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ChatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService{

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatRoomRepositorySupport chatRoomRepositorySupport;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<ChatRoomGetRes> getListChatRoom(long userId) {
        List<ChatRoomGetRes> result = new ArrayList<>();
        List<ChatRoom> queryResult = chatRoomRepositorySupport.findChatListByuserId(userId);
        for(ChatRoom chatRoom : queryResult)
            result.add(ChatRoomGetRes.from(chatRoom));

        return result;
    }

    @Override
    public ChatRoom startChat(ChatRoomStartPostReq chatRoomStartPostReq) {
        Long userId1 = chatRoomStartPostReq.getSendUserId();
        Long userId2 = chatRoomStartPostReq.getReceiveUserId();

        if (chatRoomRepositorySupport.isDuplicateChat(userId1,userId2)) //이미 채팅중이면
            return null;

        Optional<User> user1 = userRepository.findById(userId1);
        Optional<User> user2 = userRepository.findById(userId2);

        if (!user1.isPresent() || !user2.isPresent())   //유저가 존재하지 않으면
            return null;

        ChatRoom chatRoom = ChatRoom.builder()
                .sendUser(user1.get())
                .receiveUser(user2.get())
                .build();

        chatRoomRepository.save(chatRoom);

        return chatRoom;
    }
}
