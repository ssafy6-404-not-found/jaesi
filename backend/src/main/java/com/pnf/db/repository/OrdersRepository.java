package com.pnf.db.repository;

import com.pnf.db.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAllByBuyerId(Long buyerId, Pageable pageable);
    Page<Orders> findAllBySellerId(Long sellerId, Pageable pageable);
}
