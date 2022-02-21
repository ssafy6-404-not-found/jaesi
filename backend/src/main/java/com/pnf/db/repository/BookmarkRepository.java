package com.pnf.db.repository;

import com.pnf.db.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Page<Bookmark> findAllByUserId(Long userId, Pageable pageable);
    Optional<Bookmark> findByUserIdAndProductId(Long userId, Long productId);
    @Transactional
    Long deleteBookmarkByUserIdAndProductId(Long userId, Long productId);
}
