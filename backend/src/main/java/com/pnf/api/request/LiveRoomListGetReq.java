package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LiveRoomListGetReq")
public class LiveRoomListGetReq {
    @ApiModelProperty(name = "category", required = true, example = "0 - 전체, 1 - 개발, 2 - 디자인, 3 - 핸드메이드")
    Integer category = 0;
    @ApiModelProperty(name = "sortType", example = "0 - 최신순, 1 - 인기순 (기본값 - 0)")
    Integer sortType = 0;
    @ApiModelProperty(name = "currPage", required = true, example = "현재 페이지 (1부터 시작)")
    Integer currPage = 1;
    @ApiModelProperty(name = "perPage", example = "한페이지당 갯수 (기본값 - 10)")
    Integer perPage = 10;
}
