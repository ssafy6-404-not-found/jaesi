package com.pnf.api.repository;

import com.pnf.db.entity.Orders;
import com.pnf.db.repository.OrdersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersRepositoryTest {

    @Autowired
    OrdersRepository repository;

    @Test
    public void 판매자주문내역_반환() {
        Long sellerId = 1L;
        Pageable pageable = PageRequest.of(0,3);

        Page<Orders> orders = repository.findAllBySellerId(sellerId,pageable);
        System.out.println(orders.toString());

    }

    @Test
    public void 구매자주문내역_반환() {
        Long buyerId = 2L;
        Pageable pageable = PageRequest.of(0,3);

        Page<Orders> orders = repository.findAllByBuyerId(buyerId,pageable);
        System.out.println(orders.toString());

    }



}
