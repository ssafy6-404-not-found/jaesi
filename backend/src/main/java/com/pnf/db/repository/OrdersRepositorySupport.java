package com.pnf.db.repository;

import com.pnf.db.entity.Orders;
import com.pnf.db.entity.QOrders;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QOrders qOrders = QOrders.orders;

    public List<Orders> findByBuyer(Long buyerId) {
        List<Orders> list = jpaQueryFactory.selectFrom(qOrders).where(qOrders.buyer.id.eq(buyerId)).fetch();
        return list;
    }

    public List<Orders> findBySeller(Long sellerId) {
        List<Orders> list = jpaQueryFactory.selectFrom(qOrders).where(qOrders.seller.id.eq(sellerId)).fetch();
        return list;
    }
}
