package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LiveRoomUpdateReq")
public class LiveRoomUpdateReq {
    @ApiModelProperty(name = "호스트유저id", required = true)
    Long hostId;
    @ApiModelProperty(name = "썸네일주소")
    String thumbnail;
    @ApiModelProperty(name = "실시간 시청자수")
    Long realTimeViewers;
}
