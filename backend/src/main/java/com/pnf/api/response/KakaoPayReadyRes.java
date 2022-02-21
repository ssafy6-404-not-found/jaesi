package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@ApiModel("KakaoPayReadyRes")
public class KakaoPayReadyRes extends BaseResponseBody{
    @ApiModelProperty(name="tid")
    String tid;
    @ApiModelProperty(name="next_redirect_pc_url")
    String next_redirect_pc_url;
    @ApiModelProperty(name="created_at")
    LocalDateTime created_at;

    public static KakaoPayReadyRes of(Integer statusCode, String message, KakaoPayReadyRes kakaoPayReadyRes) {
        KakaoPayReadyRes body = new KakaoPayReadyRes();
        body.setStatusCode(statusCode);
        body.setMessage(message);
        if (kakaoPayReadyRes != null) {
            body.setTid(kakaoPayReadyRes.getTid());
            body.setNext_redirect_pc_url(kakaoPayReadyRes.getNext_redirect_pc_url());
            body.setCreated_at(kakaoPayReadyRes.getCreated_at());
        }

        return body;
    }
}
