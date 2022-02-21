package com.pnf.api.service;

import com.pnf.api.request.KakaoPayReadyReq;
import com.pnf.api.response.KakaoPayApprovalRes;
import com.pnf.api.response.KakaoPayReadyRes;

public interface PayService {
    KakaoPayReadyRes kakaoPayReady(KakaoPayReadyReq kakaoPayReadyReq);
    KakaoPayApprovalRes kakaoPayApprove(String pgToken);
}
