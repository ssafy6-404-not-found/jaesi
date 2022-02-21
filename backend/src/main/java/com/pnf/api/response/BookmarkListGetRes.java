package com.pnf.api.response;

import com.pnf.db.entity.Bookmark;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("BookmarkGetRes")
public class BookmarkListGetRes {
    @ApiModelProperty(name = "유저 id",required = true)
    Long userId;
    @ApiModelProperty(name = "서비스 정보",required = true)
    Long productId;
    @ApiModelProperty
    String userNickname;
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

    public static BookmarkListGetRes from(Bookmark b) {
        return BookmarkListGetRes.builder()
                .userId(b.getUser().getId())
                .productId(b.getProduct().getId())
                .userNickname(b.getUser().getNickname())
                .title(b.getProduct().getTitle())
                .content(b.getProduct().getContent())
                .price(b.getProduct().getPrice())
                .hit(b.getProduct().getHit())
                .rating(b.getProduct().getRating())
                .sell_count(b.getProduct().getSellCount())
                .thumbnail(b.getProduct().getThumbnail())
                .img(b.getProduct().getImg())
                .productionPeriod(b.getProduct().getProductionPeriod())
                .custom(b.getProduct().getCustom())
                .regTime(b.getProduct().getRegTime())
                .build();
    }
}
