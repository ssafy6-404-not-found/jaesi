package com.pnf.api.service;

import com.pnf.api.request.OrdersUpdateReq;
import com.pnf.db.entity.Orders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersServiceTest {

    @Autowired
    OrdersService service;

    @Test
    public void 주문수정() {
        Long ordersId = 1L;
        OrdersUpdateReq ordersUpdateReq = new OrdersUpdateReq();

        ordersUpdateReq.setOrdersId(ordersId);
        ordersUpdateReq.setDueDate(null);

        boolean orders = service.updateOrders(ordersUpdateReq);
    }
}
