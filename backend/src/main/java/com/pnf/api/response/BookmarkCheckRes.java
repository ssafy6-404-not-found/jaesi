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
@ApiModel("BookmarkCheckResponse")
public class BookmarkCheckRes extends BaseResponseBody {
	@ApiModelProperty(name="찜 유무", example = "true - 찜, false - 찜아님")
	Boolean useBookmark;
	
	public static BookmarkCheckRes of(Integer statusCode, String message, Boolean userBookmark) {
		BookmarkCheckRes res = new BookmarkCheckRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setUseBookmark(userBookmark);
		return res;
	}
}
