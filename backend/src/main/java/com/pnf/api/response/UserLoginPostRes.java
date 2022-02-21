package com.pnf.api.response;

import com.pnf.db.entity.Expert;
import com.pnf.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 유저 로그인 API 요청에 대한 응답값
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes extends BaseResponseBody{
	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	String accessToken;
	@ApiModelProperty(name = "", example = "")
	Integer userId;
	@ApiModelProperty(name="유저 Email", example="your_email")
	String email;
	@ApiModelProperty(name="유저 Nickname", example="your_nickname")
	String nickname;
	@ApiModelProperty(name="유저 Name", example="your_name")
	String name;
	@ApiModelProperty(name = "프로필 사진", example = "https://asdf.asdf")
	String profilePhoto;
	@ApiModelProperty(name = "이메일 인증 여부", example = "true")
	Boolean emailCertified;
	@ApiModelProperty(name = "전문가 여부", example = "true")
	Boolean isProfessional;


	public static UserLoginPostRes of(Integer statusCode, String message, String accessToken, User user) {
		UserLoginPostRes res = new UserLoginPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccessToken(accessToken);
		if (user != null) {
			res.setUserId(Math.toIntExact(user.getId()));
			res.setEmail(user.getEmail());
			res.setNickname(user.getNickname());
			res.setName(user.getName());
			res.setProfilePhoto(user.getProfilePhoto());
			res.setEmailCertified(user.getEmailCertified());
			res.setIsProfessional(false);
		}
		return res;
	}

	public static UserLoginPostRes of(Integer statusCode, String message, String accessToken, Expert expert) {
		UserLoginPostRes res = new UserLoginPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccessToken(accessToken);
		if (expert != null) {
			res.setUserId(Math.toIntExact(expert.getId()));
			res.setEmail(expert.getEmail());
			res.setNickname(expert.getNickname());
			res.setName(expert.getName());
			res.setProfilePhoto(expert.getProfilePhoto());
			res.setEmailCertified(expert.getEmailCertified());
			res.setIsProfessional(true);
		}
		return res;
	}
}
