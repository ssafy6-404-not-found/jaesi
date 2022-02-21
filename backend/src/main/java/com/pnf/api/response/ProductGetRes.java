package com.pnf.api.response;


import com.pnf.db.entity.Product;
import com.pnf.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ProductGetRes")
public class ProductGetRes {
    @ApiModelProperty(name = "")
    Long productId;
    @ApiModelProperty(name = "")
    Long user;
    @ApiModelProperty
    String userNickname;
    @ApiModelProperty(name = "")
    Long category;
    @ApiModelProperty(name = "")
    Set<Review> reviews;
    @ApiModelProperty(name = "")
    String title;
    @ApiModelProperty(name = "")
    String content;
    @ApiModelProperty(name = "")
    Integer price;
    @ApiModelProperty(name = "")
    Long hit;
    @ApiModelProperty(name = "")
    Float rating;
    @ApiModelProperty(name = "")
    Long sell_count;
    @ApiModelProperty(name = "")
    String thumbnail;
    @ApiModelProperty(name = "")
    String img;
    @ApiModelProperty(name = "")
    Integer productionPeriod;
    @ApiModelProperty(name = "")
    Boolean custom;
    @ApiModelProperty(name = "")
    LocalDateTime regTime;

    public static ProductGetRes from(Product p) {
        return ProductGetRes.builder()
                .productId(p.getId())
                .user(p.getUser().getId())
                .userNickname(p.getUser().getNickname())
                .category(p.getCategory().getId())
                .reviews(p.getReviews())
                .title(p.getTitle())
                .content(p.getContent())
                .price(p.getPrice())
                .hit(p.getHit())
                .rating(p.getRating())
                .sell_count(p.getSellCount())
                .thumbnail(p.getThumbnail())
                .img(p.getImg())
                .productionPeriod(p.getProductionPeriod())
                .custom(p.getCustom())
                .regTime(p.getRegTime()).build();
    }
}
