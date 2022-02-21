package com.pnf.api.controller;

import com.pnf.api.request.OrdersBuyerListGetReq;
import com.pnf.api.request.OrdersSellerListGetReq;
import com.pnf.api.request.OrdersRegisterPostReq;
import com.pnf.api.request.OrdersUpdateReq;
import com.pnf.api.response.OrdersPostRes;
import com.pnf.api.response.OrdersRes;
import com.pnf.api.service.OrdersService;
import com.pnf.api.service.ProductService;
import com.pnf.api.response.BaseResponseBody;
import com.pnf.db.entity.Orders;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "주문", tags = {"Orders"})
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    ProductService productService;

    @Autowired
    OrdersService ordersService;

    @PostMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "주문 등록 - jwt", notes = "주문 정보를 생성합니다. 주문정보는 결제하기 전까지는 주문 조회를 해도 보이지 않습니다. 꼭 결제를 진행해주세요")
    public ResponseEntity<? extends OrdersPostRes> registerOrders(@RequestBody OrdersRegisterPostReq ordersRegisterPostReq) {
        Orders order;
        if ((order = ordersService.registerOrders(ordersRegisterPostReq)) != null) {
            return ResponseEntity.status(200).body(OrdersPostRes.of(200, "Success", order.getId()));
        } else {
            return ResponseEntity.status(400).body(OrdersPostRes.of(400, "Fail", null));
        }
    }

    @GetMapping("/{ordersId}")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "주문 상세정보 조회 - jwt")
    public ResponseEntity<OrdersRes> findOrdersById(@PathVariable("ordersId") Long ordersId) {
        OrdersRes ordersRes = ordersService.selectOrders(ordersId);
        return new ResponseEntity<OrdersRes>(ordersRes, HttpStatus.OK);
    }

    @GetMapping("/customer/{buyerId}")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "구매자의 주문내역 조회 - jwt", notes = "결제하지 않은 주문내역은 보이지 않습니다.")
    public ResponseEntity<Page<OrdersRes>>listCustomerOrders(@ApiParam(value = "구매자id와 페이징 부가정보", required = true)OrdersBuyerListGetReq ordersBuyerListGetReq){
        Page<OrdersRes> ordersResList = ordersService.selectCustomerOrders(ordersBuyerListGetReq);
        return new ResponseEntity(ordersResList, HttpStatus.OK);
    }

    @PutMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "주문 수정 - jwt")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 402, message = "업데이트 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> updateOrders(@RequestBody OrdersUpdateReq ordersUpdateReq) {

        boolean isSuccess = ordersService.updateOrders(ordersUpdateReq);
        if(isSuccess) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        }else {
            return ResponseEntity.status(402).body(BaseResponseBody.of(402,"Fail"));
        }
    }

    @GetMapping("/expert/{sellerId}")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "판매자 자신에게 들어온 주문내역 조회 - jwt", notes = "결제하지 않은 주문내역은 보이지 않습니다.")
    public ResponseEntity<Page<OrdersRes>> listExpertOrders (@ApiParam(value = "판매자id와 페이징 부가정보", required = true) OrdersSellerListGetReq ordersSellerListGetReq) {
        Page<OrdersRes> ordersResList = ordersService.selectExpertOrders(ordersSellerListGetReq);
        return new ResponseEntity(ordersResList, HttpStatus.OK);
    }
}
