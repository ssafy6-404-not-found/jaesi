package com.pnf.api.service;

import com.pnf.api.request.ReviewRegisterPostReq;
import com.pnf.api.response.ReviewListGetReq;
import com.pnf.api.response.ReviewRes;
import org.springframework.data.domain.Page;

public interface ReviewService {
    boolean registerReview(ReviewRegisterPostReq reviewRegisterPostReq);
    ReviewRes selectReview(Long reviewId);
    Page<ReviewRes> getListExpertReview(ReviewListGetReq reviewListGetReq);
}
