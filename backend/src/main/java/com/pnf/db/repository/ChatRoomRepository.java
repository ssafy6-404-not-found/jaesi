package com.pnf.db.repository;

import com.pnf.db.entity.ChatRoom;
import com.pnf.db.entity.LiveRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
