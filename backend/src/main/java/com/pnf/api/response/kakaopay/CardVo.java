package com.pnf.api.response.kakaopay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AmountVo")
public class CardVo {

    @ApiModelProperty(name="purchase_corp")
    String purchase_corp;
    @ApiModelProperty(name="purchase_corp_code")
    String purchase_corp_code;
    @ApiModelProperty(name="issuer_corp")
    String issuer_corp;
    @ApiModelProperty(name="issuer_corp_code")
    String issuer_corp_code;
    @ApiModelProperty(name="bin")
    String bin;
    @ApiModelProperty(name="card_type")
    String card_type;
    @ApiModelProperty(name="install_month")
    String install_month;
    @ApiModelProperty(name="approved_id")
    String approved_id;
    @ApiModelProperty(name="card_mid")
    String card_mid;
    @ApiModelProperty(name="interest_free_install")
    String interest_free_install;
    @ApiModelProperty(name="card_item_code")
    String card_item_code;

}
