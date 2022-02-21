package com.pnf.api.response;

import com.pnf.db.entity.LiveRoom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("LiveRoomGetRes")
public class LiveRoomGetRes {

    @ApiModelProperty(name = "")
    Long id;
    @ApiModelProperty(name = "")
    Long hostId;
    @ApiModelProperty(name = "")
    Long categoryId;
    @ApiModelProperty(name = "")
    String hostNickname;
    @ApiModelProperty(name = "")
    String title;
    @ApiModelProperty(name = "")
    String description;
    @ApiModelProperty(name = "")
    String thumbnail;
    @ApiModelProperty(name = "")
    LocalDateTime startTime;
    @ApiModelProperty(name = "")
    LocalDateTime endTime;
    @ApiModelProperty(name = "")
    Long hit;
    @ApiModelProperty(name = "")
    Long realTimeViewers;

    public static LiveRoomGetRes of(LiveRoom l) {
        LiveRoomGetRes liveRoomGetRes = LiveRoomGetRes.builder()
                .id(l.getId())
                .hostId(l.getUser().getId())
                .categoryId(l.getCategory().getId())
                .hostNickname(l.getUser().getNickname())
                .title(l.getTitle())
                .description(l.getDescription())
                .thumbnail(l.getThumbnail())
                .startTime(l.getStartTime())
                .endTime(l.getEndTime())
                .hit(l.getHit())
                .realTimeViewers(l.getRealTimeViewers())
                .build();
        return liveRoomGetRes;
    }
}
