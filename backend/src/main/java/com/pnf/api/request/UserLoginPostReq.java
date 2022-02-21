package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API 요청에 필요한 리퀘스트 바디
 */
@Getter
@Setter
@ApiModel("UserLoginPostRequest")
public class UserLoginPostReq {
	@ApiModelProperty(name="유저 email", required = true, example="your_email")
	String email;
	@ApiModelProperty(name="유저 Password", required = true, example="your_password")
	String password;
}
