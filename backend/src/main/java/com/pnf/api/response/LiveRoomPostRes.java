package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LiveRoomPostRes")
public class LiveRoomPostRes extends BaseResponseBody{
    @ApiModelProperty(name = "liveId")
    Long liveRoomId;

    public static LiveRoomPostRes of(Integer statusCode, String message, Long liveRoomId) {
        LiveRoomPostRes body = new LiveRoomPostRes();
        body.statusCode = statusCode;
        body.message = message;
        body.liveRoomId = liveRoomId;
        return body;
    }
}
