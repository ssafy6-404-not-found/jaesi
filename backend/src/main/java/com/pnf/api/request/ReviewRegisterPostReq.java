package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ReviewRegisterPostReq")
public class ReviewRegisterPostReq {
    @ApiModelProperty(name = "서비스id", required = true)
    Long productId;
    @ApiModelProperty(name = "판매 유저id", required = true)
    Long sellerId;
    @ApiModelProperty(name = "리뷰작성 유저id", required = true)
    Long buyerId;
    @ApiModelProperty(name = "주문 id", required = true)
    Long ordersId;
    @ApiModelProperty(name = "평점", required = true)
    Float rating;
    @ApiModelProperty(name = "리뷰 내용", required = true)
    String content;
    @ApiModelProperty(name = "이미지주소")
    String img;
}
