package com.pnf.api.service;

import com.pnf.api.request.OrdersBuyerListGetReq;
import com.pnf.api.request.OrdersSellerListGetReq;
import com.pnf.api.request.OrdersRegisterPostReq;
import com.pnf.api.request.OrdersUpdateReq;
import com.pnf.api.response.OrdersRes;
import com.pnf.db.entity.Orders;
import com.pnf.db.entity.Product;
import com.pnf.db.entity.Review;
import com.pnf.db.entity.User;
import com.pnf.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrdersRepositorySupport ordersRepositorySupport;
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Orders registerOrders(OrdersRegisterPostReq ordersRegisterPostReq) {

        Optional<User> optSeller = userRepository.findById(ordersRegisterPostReq.getSellerId());
        Optional<User> optBuyer = userRepository.findById(ordersRegisterPostReq.getBuyerId());
        Optional<Product> optProduct = productRepository.findById(ordersRegisterPostReq.getProductId());

        if (!optSeller.isPresent() || !optBuyer.isPresent() || !optProduct.isPresent()) {   //데이터가 없으면
            return null;
        }

        User seller = optSeller.get();
        User buyer = optBuyer.get();
        Product product = optProduct.get();

        Orders order = Orders.builder()
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .price(ordersRegisterPostReq.getPrice())
                .count(ordersRegisterPostReq.getCount())
                .description(ordersRegisterPostReq.getDescription())
                .zipcode(ordersRegisterPostReq.getZipcode())
                .baseAddress(ordersRegisterPostReq.getBaseAddress())
                .detailAddress(ordersRegisterPostReq.getDetailAddress())
                .receiverName(ordersRegisterPostReq.getReceiverName())
                .receiverPhone(ordersRegisterPostReq.getReceiverPhone())
                .build();
        ordersRepository.save(order);
        return order;
    }

    @Override
    public OrdersRes selectOrders(Long ordersId) {

        Orders orders = ordersRepository.findById(ordersId).get();
        Review review = reviewRepository.findByOrdersId(ordersId);

        OrdersRes ordersRes = OrdersRes.builder()
                .ordersId(orders.getId())
                .sellerId(orders.getSeller().getId())
                .sellerNickname(orders.getSeller().getNickname())
                .productId(orders.getProduct().getId())
                .price(orders.getPrice())
                .count(orders.getCount())
                .description(orders.getDescription())
                .zipcode(orders.getZipcode())
                .baseAddress(orders.getBaseAddress())
                .detailAddress(orders.getDetailAddress())
                .receiverName(orders.getReceiverName())
                .receiverPhone(orders.getReceiverPhone())
                .state(orders.getState())
                .dueDate(orders.getDueDate())
                .endDate(orders.getEndDate())
                .isWrittenReview((review != null)?true:false)
                .isPayComplete(orders.getIsPayComplete())
                .build();

        return ordersRes;
    }

    @Override
    public Page<OrdersRes> selectCustomerOrders(OrdersBuyerListGetReq ordersBuyerListGetReq) {
        Long buyerId = ordersBuyerListGetReq.getBuyerId();
        Sort sort = Sort.by("endDate").ascending().and(Sort.by("regTime").descending());
        Pageable pageable = PageRequest.of(ordersBuyerListGetReq.getCurrPage() - 1, ordersBuyerListGetReq.getPerPage(), sort);
        Page<OrdersRes> orders = ordersRepository.findAllByBuyerId(buyerId, pageable).map(OrdersRes::from);

        return orders;
    }

    @Override
    public boolean updateOrders(OrdersUpdateReq ordersUpdateReq) {

        Orders orders = ordersRepository.findById(ordersUpdateReq.getOrdersId()).get();

        if (orders == null) {
            return false;
        } else {
            orders.setPrice((ordersUpdateReq.getPrice() != null) ? ordersUpdateReq.getPrice() : orders.getPrice());
            orders.setCount((ordersUpdateReq.getCount() != null) ? ordersUpdateReq.getCount() : orders.getCount());
            orders.setDescription((ordersUpdateReq.getDescription() != null) ? ordersUpdateReq.getDescription() : orders.getDescription());
            orders.setZipcode((ordersUpdateReq.getZipcode() != null) ? ordersUpdateReq.getZipcode() : orders.getZipcode());
            orders.setBaseAddress((ordersUpdateReq.getBaseAddress() != null) ? ordersUpdateReq.getBaseAddress() : orders.getBaseAddress());
            orders.setDetailAddress((ordersUpdateReq.getDetailAddress() != null) ? ordersUpdateReq.getDetailAddress() : orders.getDetailAddress());
            orders.setState((ordersUpdateReq.getState() != null) ? ordersUpdateReq.getState() : orders.getState());
            orders.setDueDate((ordersUpdateReq.getDueDate() != null) ? ordersUpdateReq.getDueDate() : orders.getDueDate());
            orders.setEndDate((ordersUpdateReq.getEndDate() != null) ? ordersUpdateReq.getEndDate() : orders.getEndDate());

            ordersRepository.save(orders);
        }

        return true;
    }

    @Override
    public Page<OrdersRes> selectExpertOrders(OrdersSellerListGetReq ordersSellerListGetReq) {
        Long sellerId = ordersSellerListGetReq.getSellerId();
        Sort sort = Sort.by("endDate").ascending().and(Sort.by("regTime").descending());
        Pageable pageable = PageRequest.of(ordersSellerListGetReq.getCurrPage() - 1, ordersSellerListGetReq.getPerPage(), sort);
        Page<OrdersRes> orders = ordersRepository.findAllBySellerId(sellerId, pageable).map(OrdersRes::from);

        return orders;
    }
}
