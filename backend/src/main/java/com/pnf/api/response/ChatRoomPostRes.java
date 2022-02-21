package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ChatRoomPostRes")
public class ChatRoomPostRes extends BaseResponseBody{
    @ApiModelProperty(name = "채팅방id")
    Long chatId;

    public static ChatRoomPostRes of(Integer statusCode, String message,Long chatId) {
        ChatRoomPostRes body = new ChatRoomPostRes();
        body.message = message;
        body.statusCode = statusCode;
        body.chatId = chatId;
        return body;
    }
}
