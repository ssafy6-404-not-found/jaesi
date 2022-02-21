package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BookmarkAddReq")
public class BookmarkGetReq {
    @ApiModelProperty(name = "userId",required = true)
    Long userId;
    @ApiModelProperty(name = "productId",required = true)
    Long productId;
}
