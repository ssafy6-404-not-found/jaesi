package com.pnf.api.response;

import com.pnf.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 요청에 대한 응답값
 */
@Getter
@Setter
@ApiModel("UserGetResponse")
public class UserGetRes extends BaseResponseBody{

	@ApiModelProperty(name="유저 id")
	Long userId;
	@ApiModelProperty(name="유저 Email")
	String email;
	@ApiModelProperty(name="유저 이름")
	String name;
	@ApiModelProperty(name="유저 닉네임")
	String nickname;
	@ApiModelProperty(name="유저 우편번호")
	String zipcode;
	@ApiModelProperty(name="유저 기본주소")
	String baseAddress;
	@ApiModelProperty(name="유저 상세주소")
	String detailAddress;
	@ApiModelProperty(name="유저 전화번호")
	String phone;
	@ApiModelProperty(name="유저 소셜타입")
	Integer socialType;
	@ApiModelProperty(name="유저 프로필사진")
	String profilePhoto;
	@ApiModelProperty(name="유저 이메일인증 여부")
	Boolean emailCertified;
	@ApiModelProperty(name = "전문가 여부", example = "true")
	Boolean isProfessional;

	public static UserGetRes of(Integer statusCode, String message, User user) {
		UserGetRes res = new UserGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if (user != null) {
			res.setUserId(user.getId());
			res.setEmail(user.getEmail());
			res.setName(user.getName());
			res.setNickname(user.getNickname());
			res.setZipcode(user.getZipcode());
			res.setBaseAddress(user.getBaseAddress());
			res.setDetailAddress(user.getDetailAddress());
			res.setPhone(user.getPhone());
			res.setSocialType(user.getSocialType());
			res.setProfilePhoto(user.getProfilePhoto());
			res.setEmailCertified(user.getEmailCertified());
			res.setIsProfessional(false);
		}
		return res;
	}
}
