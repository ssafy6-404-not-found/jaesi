package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ReviewListGetReq")
public class ReviewListGetReq {
    @ApiModelProperty(name = "currPage", required = true, example = "현재 페이지 (1부터 시작)")
    Integer currPage = 1;
    @ApiModelProperty(name = "perPage", example = "한페이지당 갯수")
    Integer perPage = 10;
    @ApiModelProperty(name = "sellerId", required = true, example = "판매자 id")
    Long sellerId;
}
