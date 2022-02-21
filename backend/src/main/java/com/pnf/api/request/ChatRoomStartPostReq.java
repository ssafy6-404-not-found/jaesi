package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ChatRoomStartPostReq")
public class ChatRoomStartPostReq {
    @ApiModelProperty(name = "유저id 1",required = true)
    Long sendUserId;
    @ApiModelProperty(name = "유저id 2",required = true)
    Long receiveUserId;
}
