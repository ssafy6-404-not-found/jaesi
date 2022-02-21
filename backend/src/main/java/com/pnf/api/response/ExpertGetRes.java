package com.pnf.api.response;

import com.pnf.db.entity.Expert;
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
@ApiModel("ExpertGetResponse")
public class ExpertGetRes extends UserGetRes{

	@ApiModelProperty(name="유저 BusinessName", example="your_businessname")
	String businessName;
	@ApiModelProperty(name="유저 BusinessNumber", example="your_businessnumber")
	String businessNumber;
	@ApiModelProperty(name="유저 BusinessType", example="your_businesstype")
	String businessType;

	public static ExpertGetRes of(Integer statusCode, String message, Expert expert) {
		ExpertGetRes res = new ExpertGetRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		if (expert != null) {
			res.setUserId(expert.getId());
			res.setEmail(expert.getEmail());
			res.setName(expert.getName());
			res.setNickname(expert.getNickname());
			res.setZipcode(expert.getZipcode());
			res.setBaseAddress(expert.getBaseAddress());
			res.setDetailAddress(expert.getDetailAddress());
			res.setPhone(expert.getPhone());
			res.setSocialType(expert.getSocialType());
			res.setProfilePhoto(expert.getProfilePhoto());
			res.setEmailCertified(expert.getEmailCertified());
			res.setIsProfessional(true);
			res.setBusinessName(expert.getBusinessName());
			res.setBusinessNumber(expert.getBusinessNumber());
			res.setBusinessType(expert.getBusinessType());
		}
		return res;
	}
}
