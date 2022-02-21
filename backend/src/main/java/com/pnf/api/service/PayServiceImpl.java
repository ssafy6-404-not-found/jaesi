package com.pnf.api.service;

import com.pnf.api.request.KakaoPayReadyReq;
import com.pnf.api.response.KakaoPayApprovalRes;
import com.pnf.api.response.KakaoPayReadyRes;
import com.pnf.db.entity.Orders;
import com.pnf.db.entity.PayInfo;
import com.pnf.db.entity.Product;
import com.pnf.db.entity.User;
import com.pnf.db.repository.OrdersRepository;
import com.pnf.db.repository.PayInfoRepository;
import com.pnf.db.repository.ProductRepository;
import com.pnf.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class PayServiceImpl implements PayService{

    private static final String HOST = "https://kapi.kakao.com";
    private static final String ADMIN_KEY = "kakao admin key";

    @Autowired
    private PayInfoRepository payInfoRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;

    private KakaoPayReadyRes kakaoPayReadyRes;

    @Override
    public KakaoPayReadyRes kakaoPayReady(KakaoPayReadyReq kakaoPayReadyReq) {

        RestTemplate restTemplate = new RestTemplate();


        Optional<User> optUser = userRepository.findById(kakaoPayReadyReq.getBuyerId());
        Optional<Orders> optOrders = ordersRepository.findById(kakaoPayReadyReq.getOrderId());
        Optional<Product> optProduct = productRepository.findById(kakaoPayReadyReq.getProductId());

        if (!optUser.isPresent() || !optOrders.isPresent() || !optProduct.isPresent())  //세개중 하나라도 없으면
            return null;    //결제 실패

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", String.valueOf(kakaoPayReadyReq.getOrderId()));
        params.add("partner_user_id", String.valueOf(kakaoPayReadyReq.getBuyerId()));
        params.add("item_name", kakaoPayReadyReq.getProductName());
        params.add("item_code", String.valueOf(kakaoPayReadyReq.getProductId()));
        params.add("quantity", String.valueOf(kakaoPayReadyReq.getCount()));
        params.add("total_amount", String.valueOf(kakaoPayReadyReq.getTotalPrice()));
        params.add("tax_free_amount", String.valueOf(kakaoPayReadyReq.getTaxFreePrice()));
        params.add("approval_url", kakaoPayReadyReq.getServerBaseUrl()+"/pay/result/success");
        params.add("cancel_url", kakaoPayReadyReq.getServerBaseUrl()+"/pay/result/cancel");
        params.add("fail_url", kakaoPayReadyReq.getServerBaseUrl()+"/pay/result/fail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        try {
            kakaoPayReadyRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyRes.class);
            PayInfo payInfo = PayInfo.builder()
                    .tid(kakaoPayReadyRes.getTid())
                    .partnerBuyerId(kakaoPayReadyReq.getBuyerId())
                    .partnerOrderId(kakaoPayReadyReq.getOrderId())
                    .totalPrice(kakaoPayReadyReq.getTotalPrice())
                    .build();

            payInfoRepository.save(payInfo);
            return kakaoPayReadyRes;

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public KakaoPayApprovalRes kakaoPayApprove(String pgToken) {
        RestTemplate restTemplate = new RestTemplate();
        Optional<PayInfo> optPayInfo = payInfoRepository.findById(kakaoPayReadyRes.getTid());

        if (!optPayInfo.isPresent())    //페이정보 없으면
            return null;
        PayInfo payInfo = optPayInfo.get();
        Orders orders = ordersRepository.findById(payInfo.getPartnerOrderId()).get();


        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", payInfo.getTid());
        params.add("partner_order_id", String.valueOf(payInfo.getPartnerOrderId()));
        params.add("partner_user_id", String.valueOf(payInfo.getPartnerBuyerId()));
        params.add("pg_token", pgToken);
        params.add("total_amount", String.valueOf(payInfo.getTotalPrice()));

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            KakaoPayApprovalRes kakaoPayApprovalRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalRes.class);
            if (kakaoPayApprovalRes != null) {  //결제 승인이 되었으면
                orders.setIsPayComplete(true);  //결제완료 표시
                ordersRepository.save(orders);
                return kakaoPayApprovalRes;
            }

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
