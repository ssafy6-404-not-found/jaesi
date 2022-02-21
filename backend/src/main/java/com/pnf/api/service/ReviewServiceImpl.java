package com.pnf.api.service;

import com.pnf.api.request.ReviewRegisterPostReq;
import com.pnf.api.response.ReviewListGetReq;
import com.pnf.api.response.ReviewRes;
import com.pnf.db.entity.Orders;
import com.pnf.db.entity.Product;
import com.pnf.db.entity.Review;
import com.pnf.db.entity.User;
import com.pnf.db.repository.OrdersRepository;
import com.pnf.db.repository.ProductRepository;
import com.pnf.db.repository.ReviewRepository;
import com.pnf.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public boolean registerReview(ReviewRegisterPostReq reviewRegisterPostReq) {

        Product product = productRepository.findById(reviewRegisterPostReq.getProductId()).get();
        User seller = userRepository.findById(reviewRegisterPostReq.getSellerId()).get();
        User buyer = userRepository.findById(reviewRegisterPostReq.getBuyerId()).get();
        Orders orders = ordersRepository.findById(reviewRegisterPostReq.getOrdersId()).get();

        Review review = Review.builder()
                .product(product)
                .seller(seller)
                .buyer(buyer)
                .orders(orders)
                .rating(reviewRegisterPostReq.getRating())
                .content(reviewRegisterPostReq.getContent())
                .img(reviewRegisterPostReq.getImg())
                .build();
        orders.setReview(review);
        reviewRepository.save(review);
        ordersRepository.save(orders);

        return true;
    }

    @Override
    public ReviewRes selectReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).get();

        ReviewRes reviewRes = ReviewRes.builder()
                .productId(review.getProduct().getId())
                .sellerId(review.getSeller().getId())
                .buyerId(review.getBuyer().getId())
                .orderId(review.getOrders().getId())
                .sellerName(review.getSeller().getNickname())
                .buyerName(review.getBuyer().getNickname())
                .rating(review.getRating())
                .content(review.getContent())
                .img(review.getImg())
                .regTime(review.getRegTime())
                .build();

        return reviewRes;
    }

    @Override
    public Page<ReviewRes> getListExpertReview(ReviewListGetReq reviewListGetReq) {

        Pageable pageable = PageRequest.of(reviewListGetReq.getCurrPage()-1, reviewListGetReq.getPerPage());
        Page<ReviewRes> reviewRes;

        reviewRes = reviewRepository.findBySellerId(reviewListGetReq.getSellerId(), pageable).map(ReviewRes::from);


        return reviewRes;
    }
}
