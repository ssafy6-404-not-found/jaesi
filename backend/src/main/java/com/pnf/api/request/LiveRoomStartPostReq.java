package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "LiveRoomStartPostReq")
public class LiveRoomStartPostReq {
    @ApiModelProperty(name = "호스트유저id", required = true)
    Long hostId;
    @ApiModelProperty(name = "카테고리id", required = true)
    Long categoryId;
    @ApiModelProperty(name = "방 비밀번호")
    String password;
    @ApiModelProperty(name = "라이브 제목", required = true)
    String title;
    @ApiModelProperty(name = "라이브 설명", required = true)
    String description;
}
