package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 엔티티 컬럼 데이터 중복 확인 Response
 */
@Getter
@Setter
@ApiModel("UserDataCheckResponse")
public class UserDataCheckRes extends BaseResponseBody {
	@ApiModelProperty(name="중복 유무", example = "true - 사용중, false - 사용중인 사람 없음")
	Boolean isUsing;
	
	public static UserDataCheckRes of(Integer statusCode, String message, Boolean isUsing) {
		UserDataCheckRes res = new UserDataCheckRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setIsUsing(isUsing);
		return res;
	}
}
