package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LiveRoomIncreaseHitReq")
public class LiveRoomIncreaseHitReq {
    @ApiModelProperty(name = "hostId",required = true)
    Long hostId;
}
