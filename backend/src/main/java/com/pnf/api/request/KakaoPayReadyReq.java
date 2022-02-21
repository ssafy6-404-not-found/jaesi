package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("KakaoPayReadyReq")
public class KakaoPayReadyReq {
    @ApiModelProperty(name = "orderId", required = true)
    Long orderId;
    @ApiModelProperty(name = "buyerId",required = true)
    Long buyerId;
    @ApiModelProperty(name = "productId",required = true)
    Long productId;
    @ApiModelProperty(name = "productName",required = true)
    String productName;
    @ApiModelProperty(name = "count",required = true)
    Integer count;
    @ApiModelProperty(name = "totalPrice",required = true)
    Integer totalPrice;
    @ApiModelProperty(name = "taxFreePrice",required = true)
    Integer taxFreePrice;
    @ApiModelProperty(name = "serverBaseUrl", example = "https://i6a404.p.ssafy.io:8000")
    String serverBaseUrl = "https://i6a404.p.ssafy.io:8000";
}
