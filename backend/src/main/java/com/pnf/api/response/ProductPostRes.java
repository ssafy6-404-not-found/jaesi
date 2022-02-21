package com.pnf.api.response;


import com.pnf.db.entity.Product;
import com.pnf.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("ProductPostRes")
public class ProductPostRes extends BaseResponseBody{
    @ApiModelProperty(name = "")
    Long productId;

    public static ProductPostRes of(Integer statusCode, String message, Long productId) {
        ProductPostRes body = new ProductPostRes();
        body.message = message;
        body.statusCode = statusCode;
        body.productId = productId;
        return body;
    }
}
