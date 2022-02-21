package com.pnf.api.service;

import com.pnf.api.request.OrdersBuyerListGetReq;
import com.pnf.api.request.OrdersRegisterPostReq;
import com.pnf.api.request.OrdersSellerListGetReq;
import com.pnf.api.request.OrdersUpdateReq;
import com.pnf.api.response.OrdersRes;
import com.pnf.db.entity.Orders;
import org.springframework.data.domain.Page;

public interface OrdersService {
    Orders registerOrders(OrdersRegisterPostReq ordersRegisterPostReq);
    OrdersRes selectOrders(Long ordersId);
    Page<OrdersRes> selectCustomerOrders(OrdersBuyerListGetReq ordersBuyerListGetReq);
    boolean updateOrders(OrdersUpdateReq ordersUpdateReq);
    Page<OrdersRes> selectExpertOrders(OrdersSellerListGetReq ordersSellerListGetReq);
}
