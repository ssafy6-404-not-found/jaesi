package com.pnf.db.repository;

import com.pnf.db.entity.LiveRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveRoomRepository extends JpaRepository<LiveRoom, Long> {

    Page<LiveRoom> findByEndTimeIsNull(Pageable pageable);

    Page<LiveRoom> findByCategoryIdAndEndTimeIsNull(Long categoryId, Pageable pageable);
    Page<LiveRoom> findByCategoryId(Long categoryId, Pageable pageable);
    LiveRoom findByUserIdAndEndTimeIsNull(Long userId);
    List<LiveRoom> findAllByUserIdAndEndTimeIsNull(Long userId);

}
