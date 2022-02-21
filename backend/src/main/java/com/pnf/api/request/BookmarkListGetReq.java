package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BookmarkListGetReq")
public class BookmarkListGetReq {
    @ApiModelProperty(name = "currPage", required = true, example = "현재 페이지 (1부터 시작)")
    Integer currPage = 1;
    @ApiModelProperty(name = "perPage", example = "한페이지당 갯수")
    Integer perPage = 10;
    @ApiModelProperty(name = "userId", required = true, example = "유저 id")
    Long userId;
}
