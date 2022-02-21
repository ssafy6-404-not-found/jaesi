package com.pnf.api.response;

import com.pnf.db.entity.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("OrdersRes")
@Builder
public class OrdersRes {
    @ApiModelProperty(name = "")
    Long ordersId;
    @ApiModelProperty(name = "")
    Long buyerId;
    @ApiModelProperty(name = "")
    Long sellerId;
    @ApiModelProperty(name = "")
    String sellerNickname;
    @ApiModelProperty(name = "")
    Long productId;
    @ApiModelProperty(name = "")
    String productTitle;
    @ApiModelProperty(name = "")
    Integer price;
    @ApiModelProperty(name = "")
    Integer count;
    @ApiModelProperty(name = "")
    String description;
    @ApiModelProperty(name = "")
    Integer zipcode;
    @ApiModelProperty(name = "")
    String baseAddress;
    @ApiModelProperty(name = "")
    String detailAddress;
    @ApiModelProperty(name = "")
    String receiverName;
    @ApiModelProperty(name = "")
    String receiverPhone;
    @ApiModelProperty(name = "")
    Boolean state;
    @ApiModelProperty(name = "")
    Boolean isWrittenReview;
    @ApiModelProperty(name = "")
    Boolean isPayComplete;
    @ApiModelProperty(name = "")
    String productImg;
    @ApiModelProperty(name = "")
    LocalDateTime regTime;
    @ApiModelProperty(name = "")
    LocalDate dueDate;
    @ApiModelProperty(name = "")
    LocalDate endDate;

    public static OrdersRes from(Orders o) {
        return OrdersRes.builder()
                .ordersId(o.getId())
                .buyerId(o.getBuyer().getId())
                .sellerId(o.getSeller().getId())
                .sellerNickname(o.getSeller().getNickname())
                .productId(o.getProduct().getId())
                .productTitle(o.getProduct().getTitle())
                .price(o.getPrice())
                .count(o.getCount())
                .description(o.getDescription())
                .zipcode(o.getZipcode())
                .baseAddress(o.getBaseAddress())
                .detailAddress(o.getDetailAddress())
                .receiverName(o.getReceiverName())
                .receiverPhone(o.getReceiverPhone())
                .state(o.getState())
                .isWrittenReview((o.getReview() != null)? true : false)
                .isPayComplete(o.getIsPayComplete())
                .productImg(o.getProduct().getImg())
                .regTime(o.getRegTime())
                .dueDate(o.getDueDate())
                .endDate(o.getEndDate())
                .build();
    }

}
