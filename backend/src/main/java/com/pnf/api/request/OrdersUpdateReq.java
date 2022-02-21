package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@ApiModel("OrdersUpdateReq")
public class OrdersUpdateReq {
    @ApiModelProperty(name = "주문id", required = true)
    Long ordersId;
    @ApiModelProperty(name = "가격")
    Integer price;
    @ApiModelProperty(name = "갯수")
    Integer count;
    @ApiModelProperty(name = "요청사항")
    String description;
    @ApiModelProperty(name = "우편번호")
    Integer zipcode;
    @ApiModelProperty(name = "기본주소")
    String baseAddress;
    @ApiModelProperty(name = "상세주소")
    String detailAddress;
    @ApiModelProperty(name = "주문상태")
    Boolean state;
    @ApiModelProperty(name = "예상마감날짜")
    LocalDate dueDate;
    @ApiModelProperty(name = "실제마감날짜")
    LocalDate endDate;
}
