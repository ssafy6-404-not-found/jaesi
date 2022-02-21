package com.pnf.api.response;

import com.pnf.db.entity.ChatRoom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ChatRoomGetRes")
public class ChatRoomGetRes {

    @ApiModelProperty(name = "채팅방id")
    Long chatId;
    @ApiModelProperty(name = "유저아이디1")
    Long sendUserId;
    @ApiModelProperty(name = "유저이름1")
    String sendUserName;
    @ApiModelProperty(name = "유저닉네임1")
    String sendUserNickname;
    @ApiModelProperty(name = "유저프로필사진1")
    String sendUserProfile;

    @ApiModelProperty(name = "유저아이디2")
    Long receiveUserId;
    @ApiModelProperty(name = "유저이름2")
    String receiveUserName;
    @ApiModelProperty(name = "유저닉네임2")
    String receiveUserNickname;
    @ApiModelProperty(name = "유저프로필사진2")
    String receiveUserProfile;

    public static ChatRoomGetRes from(ChatRoom c) {
        return ChatRoomGetRes.builder()
                .chatId(c.getId())
                .sendUserId(c.getSendUser().getId())
                .sendUserName(c.getSendUser().getName())
                .sendUserNickname(c.getSendUser().getNickname())
                .sendUserProfile(c.getSendUser().getProfilePhoto())
                .receiveUserId(c.getReceiveUser().getId())
                .receiveUserName(c.getReceiveUser().getName())
                .receiveUserNickname(c.getReceiveUser().getNickname())
                .receiveUserProfile(c.getReceiveUser().getProfilePhoto())
                .build();
    }
}
