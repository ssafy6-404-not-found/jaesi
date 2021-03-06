package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LiveRoomEndReq")
public class LiveRoomEndReq {
    @ApiModelProperty(name = "라이브id",required = true)
    Long liveRoomId;
}
