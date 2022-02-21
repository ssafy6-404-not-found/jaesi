package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserEmailCheckRequest")
public class UserEmailCheckReq {
    @ApiModelProperty(name="유저 email", required = true)
    String email;
    @ApiModelProperty(name="이메일 인증번호", required = true)
    String token;
}
