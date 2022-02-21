package com.pnf.api.controller;

import com.pnf.api.request.ReviewRegisterPostReq;
import com.pnf.api.response.ReviewListGetReq;
import com.pnf.api.response.ReviewRes;
import com.pnf.api.service.ReviewService;
import com.pnf.api.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "리뷰", tags = {"Review"})
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "리뷰 등록 - jwt", notes = "")
    public ResponseEntity<? extends BaseResponseBody> registerReview(@RequestBody ReviewRegisterPostReq reviewRegisterPostReq) {

        if(reviewService.registerReview(reviewRegisterPostReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
        }else {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400,"Fail"));
        }
    }

    @GetMapping("/{reviewId}")
    @ApiOperation(value = "리뷰 상세보기", notes = "")
    public ResponseEntity<ReviewRes> selectReview(@PathVariable("reviewId") Long reviewId){
        ReviewRes reviewRes = reviewService.selectReview(reviewId);
        return new ResponseEntity<ReviewRes>(reviewRes, HttpStatus.OK);
    }

    @GetMapping("/expert/{sellerId}")
    @ApiOperation(value = "특정 판매자의 모든리뷰 조회")
    public ResponseEntity<Page<ReviewRes>> listExpertReview(@ApiParam (value = "목록 페이징에 필요한 부가정보와 판매자id",required = true) ReviewListGetReq reviewListGetReq) {
        return new ResponseEntity<Page<ReviewRes>>(reviewService.getListExpertReview(reviewListGetReq),HttpStatus.OK);
    }
}
