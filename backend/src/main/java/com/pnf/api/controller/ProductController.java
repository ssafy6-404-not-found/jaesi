package com.pnf.api.controller;

import com.pnf.api.request.*;
import com.pnf.api.response.*;
import com.pnf.api.service.ProductService;
import com.pnf.db.entity.Bookmark;
import com.pnf.db.entity.Product;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/*
* 서비스 관련 API 요청 처리를 위한 컨트롤러 정의
* */
@Api(value = "서비스", tags = {"Product"})
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "서비스 등록 - jwt", notes = "<strong>상품정보</strong>를 통해 판매할 서비스를 등록한다.")
    public ResponseEntity<? extends ProductPostRes> registerProduct(
            @RequestBody @ApiParam(value = "서비스 정보", required = true) ProductRegisterPostReq productRegisterPostReq){
        try {
            Product product;
            if((product = productService.registerProduct(productRegisterPostReq)) != null) {
                return ResponseEntity.status(200).body(ProductPostRes.of(200,"Success",product.getId()));
            }else {
                return ResponseEntity.status(400).body(ProductPostRes.of(400,"Fail",null));
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(ProductPostRes.of(500,"Inter error",null));
        }
    }

    @GetMapping("/category")
    @ApiOperation(value = "카테고리 목록 조회")
    public ResponseEntity<List<CategoryRes>> listCategory(){
        return new ResponseEntity<List<CategoryRes>>(productService.getListAllCategory(), HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "서비스 목록 조회")
    public ResponseEntity<Page<ProductGetRes>>listProduct(@ApiParam(value = "목록 페이징에 필요한 부가정보", required = true) ProductListGetReq productListGetReq){
        return new ResponseEntity<Page<ProductGetRes>>(productService.getListProduct(productListGetReq), HttpStatus.OK);
    }

    @GetMapping("/rank")
    @ApiOperation(value = "순위 목록 조회")
    public ResponseEntity<List<ProductGetRes>>rankListProduct(){
        return new ResponseEntity<List<ProductGetRes>>(productService.getRankListProduct(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "서비스 상세정보")
    public ResponseEntity<ProductGetRes> detailProduct(@PathVariable("productId") Long productId){
        ProductGetRes productRes = productService.selectProduct(productId);
        return new ResponseEntity<ProductGetRes>(productRes, HttpStatus.OK);
    }

    @GetMapping("/expert/{userId}")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "특정 전문가의 서비스 목록 조회 - jwt")
    public ResponseEntity<Page<ProductGetRes>>listExpertProduct(@ApiParam(value = "판매자id와 페이징 부가정보", required = true) ProductExpertListGetReq productExpertListGetReq){
        Page<ProductGetRes> productResList = productService.selectExpertProduct(productExpertListGetReq);
        return new ResponseEntity(productResList, HttpStatus.OK);
    }

    @PostMapping("/bookmark")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "찜 추가 - jwt")
    public ResponseEntity<? extends BaseResponseBody> addBookmark(@RequestBody BookmarkAddReq bookmarkAddReq) {
        if(productService.addBookmark(bookmarkAddReq)) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        } else {
            return ResponseEntity.status(400).body(BaseResponseBody.of(400, "Fail"));
        }
    }

    @GetMapping("/bookmark/{userId}")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "찜 목록 조회 - jwt")
    public ResponseEntity<Page<BookmarkListGetRes>> getListBookmark(@ApiParam(value = "유저 id와 페이징 부가정보", required = true)BookmarkListGetReq bookmarkListGetReq) {
        Page<BookmarkListGetRes> bookmarkGetResList = productService.getListBookmark(bookmarkListGetReq);
        return new ResponseEntity(bookmarkGetResList, HttpStatus.OK);
    }

    @DeleteMapping("/bookmark")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "찜 제거 - jwt")
    public ResponseEntity<? extends BaseResponseBody> deleteBookmark(@RequestBody BookmarkDeleteReq bookmarkDeleteReq) {
        productService.deleteBookmark(bookmarkDeleteReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
    }

    @GetMapping("/bookmark")
    @ApiImplicitParam(name = "jwt", value = "jwt Token", example = "Bearer JWT_TOKEN", required = true, dataTypeClass = String.class, paramType = "header")
    @ApiOperation(value = "찜 유무 확인 - jwt")
    public ResponseEntity<BookmarkCheckRes> getBookmark(@ApiParam(value = "유저 id와 product id", required = true)BookmarkGetReq bookmarkGetReq) {
        Bookmark bookmark = productService.useBookmark(bookmarkGetReq);
        if (bookmark != null)
            return ResponseEntity.status(200).body(BookmarkCheckRes.of(200,"success",true));
        else
            return ResponseEntity.status(200).body(BookmarkCheckRes.of(200,"success",false));
    }
}
