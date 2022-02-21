package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("OrdersSellerListGetReq")
public class OrdersSellerListGetReq {
    @ApiModelProperty(name = "sellerId", required = true)
    Long sellerId;
    @ApiModelProperty(name = "currPage", required = true, example = "현재 페이지 (1부터 시작)")
    Integer currPage = 1;
    @ApiModelProperty(name = "perPage", example = "한페이지당 갯수 (기본값 - 10)")
    Integer perPage = 10;
}
