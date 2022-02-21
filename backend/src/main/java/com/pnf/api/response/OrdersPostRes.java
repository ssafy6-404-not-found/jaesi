package com.pnf.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("OrdersRes")
public class OrdersPostRes extends BaseResponseBody{
    @ApiModelProperty(name = "orderId")
    Long orderId;

    public static OrdersPostRes of(Integer statusCode, String message, Long orderId) {
        OrdersPostRes ordersPostRes = new OrdersPostRes();
        ordersPostRes.setStatusCode(statusCode);
        ordersPostRes.setMessage(message);
        ordersPostRes.setOrderId(orderId);

        return ordersPostRes;
    }
}
