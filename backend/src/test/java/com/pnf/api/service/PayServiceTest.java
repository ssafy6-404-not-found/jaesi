package com.pnf.api.service;

import com.pnf.api.request.KakaoPayReadyReq;
import com.pnf.api.response.KakaoPayReadyRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceTest {

    @Autowired
    PayService service;

    @Test
    public void 결제_준비() {
        KakaoPayReadyReq kakaoPayReadyReq = new KakaoPayReadyReq();

        kakaoPayReadyReq.setOrderId(1L);
        kakaoPayReadyReq.setBuyerId(1L);
        kakaoPayReadyReq.setProductId(1L);
        kakaoPayReadyReq.setProductName("복주머니");
        kakaoPayReadyReq.setCount(1);
        kakaoPayReadyReq.setTotalPrice(10000);
        kakaoPayReadyReq.setTaxFreePrice(0);

        KakaoPayReadyRes kakaoPayReadyRes = service.kakaoPayReady(kakaoPayReadyReq);
        System.out.println(kakaoPayReadyRes);
    }
}
