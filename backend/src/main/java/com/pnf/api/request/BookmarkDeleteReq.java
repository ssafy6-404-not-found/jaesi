package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BookmarkDeleteReq")
public class BookmarkDeleteReq {
    @ApiModelProperty(name = "유저 id",required = true)
    Long userId;
    @ApiModelProperty(name = "서비스 id",required = true)
    Long productId;
}
