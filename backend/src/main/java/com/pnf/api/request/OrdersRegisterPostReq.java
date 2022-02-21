package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("OrdersRegisterPostReq")
public class OrdersRegisterPostReq {
    @ApiModelProperty(name = "판매자id", required = true)
    Long sellerId;
    @ApiModelProperty(name = "구매자id", required = true)
    Long buyerId;
    @ApiModelProperty(name = "서비스id", required = true)
    Long productId;
    @ApiModelProperty(name = "가격", required = true)
    Integer price;
    @ApiModelProperty(name = "갯수", required = true)
    Integer count;
    @ApiModelProperty(name = "요청사항", required = true)
    String description;
    @ApiModelProperty(name = "우편번호")
    Integer zipcode;
    @ApiModelProperty(name = "기본주소")
    String baseAddress;
    @ApiModelProperty(name = "상세주소")
    String detailAddress;
    @ApiModelProperty(name = "구매자 이름", required = true)
    String receiverName;
    @ApiModelProperty(name = "구매자 전화번호", required = true)
    String receiverPhone;
}
