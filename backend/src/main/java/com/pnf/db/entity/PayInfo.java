package com.pnf.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("pay_info")
public class PayInfo {

    @Id
    private String tid;

    private String pgToken;

    private Long partnerOrderId;

    private Long partnerBuyerId;

    private Integer totalPrice;

}
