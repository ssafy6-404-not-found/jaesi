package com.pnf.api.response;

import com.pnf.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ApiModel("ReviewRes")
@Builder
public class ReviewRes {
    @ApiModelProperty(name = "")
    Long productId;
    @ApiModelProperty(name = "")
    Long sellerId;
    @ApiModelProperty(name = "")
    Long buyerId;
    @ApiModelProperty(name = "")
    Long orderId;
    @ApiModelProperty(name = "")
    String sellerName;
    @ApiModelProperty(name = "")
    String buyerName;
    @ApiModelProperty(name = "")
    Float rating;
    @ApiModelProperty(name = "")
    String content;
    @ApiModelProperty(name = "")
    String img;
    @ApiModelProperty(name = "")
    LocalDateTime regTime;

    public static ReviewRes from(Review r) {
        return ReviewRes.builder()
                .productId(r.getProduct().getId())
                .sellerId(r.getSeller().getId())
                .buyerId(r.getBuyer().getId())
                .orderId(r.getOrders().getId())
                .sellerName(r.getSeller().getNickname())
                .buyerName(r.getBuyer().getNickname())
                .rating(r.getRating())
                .content(r.getContent())
                .img(r.getImg())
                .regTime(r.getRegTime())
                .build();
    }
}
