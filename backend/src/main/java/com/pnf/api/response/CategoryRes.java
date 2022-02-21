package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("CategoryRes")
public class CategoryRes {
    @ApiModelProperty(name = "카테고리 id")
    Long id;
    @ApiModelProperty(name = "카테고리 이름")
    String name;
    @ApiModelProperty(name = "카테고리 배너이미지")
    String bannerImg;
}
