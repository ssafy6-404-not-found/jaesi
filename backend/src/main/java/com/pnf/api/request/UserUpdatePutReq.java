package com.pnf.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원정보 수정 API 리퀘스트 바디 정의
 */
@Getter
@Setter
@ApiModel("UserUpdatePutRequest")
public class UserUpdatePutReq {
	@ApiModelProperty(name="유저id", required = true, example="user PK Id")
	Long userId;
	@ApiModelProperty(name="유저 password", example="your_password")
	String password;
	@ApiModelProperty(name="유저 nickname", example="your_nickname")
	String nickname;
	@ApiModelProperty(name="유저 phone", example="your_phone")
	String phone;
	@ApiModelProperty(name="유저 zipcode")
	String zipcode;
	@ApiModelProperty(name="유저 baseAddress", example="your_baseaddress")
	String baseAddress;
	@ApiModelProperty(name="유저 detailAddress", example="your_detailaddress")
	String detailAddress;
	@ApiModelProperty(name="유저 profilephoto", example="your_profilephoto")
	String profilePhoto;
	@ApiModelProperty(name="유저 businessName", example="your_businessname")
	String businessName;
	@ApiModelProperty(name="유저 businessNumber", example="your_businessnumber")
	String businessNumber;
	@ApiModelProperty(name="유저 businessType", example="your_businesstype")
	String businessType;

}
