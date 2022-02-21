package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API 요청에 필요한 리퀘스트 바디
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@ApiModelProperty(name="유저 email", required = true, example="your_email")
	String email;
	@ApiModelProperty(name="유저 password", required = true, example="your_password")
	String password;
	@ApiModelProperty(name="유저 nickname", required = true, example="your_nickname")
	String nickname;
	@ApiModelProperty(name="유저 name", required = true, example="your_name")
	String name;
	@ApiModelProperty(name="유저 phone", example="your_phone")
	String phone;
	@ApiModelProperty(name="유저 zipcode")
	String zipcode;
	@ApiModelProperty(name="유저 baseAddress", example="your_baseaddress")
	String baseAddress;
	@ApiModelProperty(name="유저 detailAddress", example="your_detailaddress")
	String detailAddress;
	@ApiModelProperty(name="가입 종류", example="0 : 일반회원가입, 1~3 : 소셜 회원가입")
	Integer socialType = 0;
	@ApiModelProperty(name="전문가여부", required = true, example="true")
	Boolean isProfessional;
	@ApiModelProperty(name="유저 businessName", example="your_businessname")
	String businessName;
	@ApiModelProperty(name="유저 businessNumber", example="your_businessnumber")
	String businessNumber;
	@ApiModelProperty(name="유저 businessType", example="your_businesstype")
	String businessType;
}
