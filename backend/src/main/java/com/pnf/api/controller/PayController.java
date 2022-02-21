package com.pnf.api.controller;

import com.pnf.api.request.KakaoPayReadyReq;
import com.pnf.api.response.BaseResponseBody;
import com.pnf.api.response.KakaoPayApprovalRes;
import com.pnf.api.response.KakaoPayReadyRes;
import com.pnf.api.service.PayService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "카카오페이 결제", tags = {"KakaoPay"})
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;

    @PostMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "카카오페이 결제 요청 - jwt", notes = "프론트에서 카카오페이 결제 요청을 이 주소로 보내야 함, orderId, buyerId, productId 모두 실제 존재하는 데이터로 보내야 함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "주문, 서비스, 유저 테이블 중 하나라도 데이터를 찾을 수 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<KakaoPayReadyRes> payWithKakao(@RequestBody @ApiParam(value = "카카오페이 결제요청정보", required = true) KakaoPayReadyReq kakaoPayReadyReq) {
        KakaoPayReadyRes kakaoPayReadyRes = payService.kakaoPayReady(kakaoPayReadyReq);
        if (kakaoPayReadyRes != null)
            return ResponseEntity.status(200).body(KakaoPayReadyRes.of(200,"success",kakaoPayReadyRes));
        else
            return ResponseEntity.status(404).body(KakaoPayReadyRes.of(404, "order or product or user Not Found", null));
    }

    @GetMapping("/result/success")
    @ApiOperation(value = "카카오페이 결제 성공", notes = "휴대폰에서 결제완료를 누르면 카카오서버에서 자동으로 이 주소로 pgToken과 함께 요청을 보냄, 그러면 백엔드에서 카카오서버로 결제승인요청을 보낸 뒤에 받은 결과를 프론트로 리턴함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 403, message = "결제정보가 불일치함"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> paySuccess(@RequestParam("pg_token") String pgToken) {
        KakaoPayApprovalRes kakaoPayApprovalRes = payService.kakaoPayApprove(pgToken);
        if (kakaoPayApprovalRes != null)
            return ResponseEntity.status(200).body(BaseResponseBody.of(200,"success"));
        else
            return ResponseEntity.status(402).body(BaseResponseBody.of(402,"payInfo not valid"));
    }

}
