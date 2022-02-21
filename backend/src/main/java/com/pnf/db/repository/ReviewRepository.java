package com.pnf.db.repository;

import com.pnf.db.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findBySellerId(Long sellerId, Pageable pageable);
    Review findByOrdersId(Long orderId);
}
