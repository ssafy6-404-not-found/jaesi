package com.pnf.db.repository;

import com.pnf.db.entity.ChatRoom;
import com.pnf.db.entity.QChatRoom;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRoomRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QChatRoom qChatRoom = QChatRoom.chatRoom;

    public List<ChatRoom> findChatListByuserId(Long userId) {
        List<ChatRoom> list= jpaQueryFactory.selectFrom(qChatRoom).where(
            qChatRoom.sendUser.id.eq(userId).or(qChatRoom.receiveUser.id.eq(userId))
        ).fetch();

        return list;
    }

    public boolean isDuplicateChat(Long userId1, Long userId2) {
        ChatRoom chatRoom = jpaQueryFactory.selectFrom(qChatRoom).where(
                ExpressionUtils.or(
                qChatRoom.sendUser.id.eq(userId1).and(qChatRoom.receiveUser.id.eq(userId2)),
                qChatRoom.sendUser.id.eq(userId2).and(qChatRoom.receiveUser.id.eq(userId1))
                )
        ).fetchOne();

        return chatRoom != null;
    }
}
