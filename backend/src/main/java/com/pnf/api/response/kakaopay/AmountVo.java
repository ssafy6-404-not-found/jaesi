package com.pnf.api.response.kakaopay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AmountVo")
public class AmountVo {

    @ApiModelProperty(name="total")
    Integer total;
    @ApiModelProperty(name="tax_free")
    Integer tax_free;
    @ApiModelProperty(name="vat")
    Integer vat;
    @ApiModelProperty(name="point")
    Integer point;
    @ApiModelProperty(name="discount")
    Integer discount;

}
