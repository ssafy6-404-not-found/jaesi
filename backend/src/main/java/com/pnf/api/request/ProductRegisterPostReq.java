package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProductRegisterPostReq")
public class ProductRegisterPostReq {
    @ApiModelProperty(name = "유저 id", required = true)
    Long user;
    @ApiModelProperty(name = "서비스 제목", required = true)
    String title;
    @ApiModelProperty(name = "서비스 내용", required = true)
    String content;
    @ApiModelProperty(name = "서비스 카테고리id", required = true)
    Long category;
    @ApiModelProperty(name = "서비스 가격", required = true)
    Integer price;
    @ApiModelProperty(name = "이미지")
    String img;
    @ApiModelProperty(name = "서비스 주문제작 여부 (기본값 - false)")
    Boolean custom = false;
    @ApiModelProperty(name = "서비스 작업기간", required = true)
    Integer productionPeriod;
}
