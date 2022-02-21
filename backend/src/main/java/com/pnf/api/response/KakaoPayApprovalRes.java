package com.pnf.api.response;

import com.pnf.api.response.kakaopay.AmountVo;
import com.pnf.api.response.kakaopay.CardVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@ApiModel("KakaoPayApprovalRes")
public class KakaoPayApprovalRes {
    @ApiModelProperty(name="aid")
    String aid;
    @ApiModelProperty(name="tid")
    String tid;
    @ApiModelProperty(name="cid")
    String cid;
    @ApiModelProperty(name="sid")
    String sid;
    @ApiModelProperty(name="partner_order_id")
    String partner_order_id;
    @ApiModelProperty(name="partner_user_id")
    String partner_user_id;
    @ApiModelProperty(name="payment_method_type")
    String payment_method_type;
    @ApiModelProperty(name="amount")
    AmountVo amount;
    @ApiModelProperty(name="card_info")
    CardVo card_info;
    @ApiModelProperty(name="item_name")
    String item_name;
    @ApiModelProperty(name="item_code")
    String item_code;
    @ApiModelProperty(name="payload")
    String payload;
    @ApiModelProperty(name="quantity")
    Integer quantity;
    @ApiModelProperty(name="tax_free_amount")
    Integer tax_free_amount;
    @ApiModelProperty(name="vat_amount")
    Integer vat_amount;
    @ApiModelProperty(name="created_at")
    LocalDateTime created_at;
    @ApiModelProperty(name="approved_at")
    LocalDateTime approved_at;
}
