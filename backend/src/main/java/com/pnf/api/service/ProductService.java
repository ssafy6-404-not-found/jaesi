package com.pnf.api.service;

import com.pnf.api.request.*;
import com.pnf.api.response.BookmarkListGetRes;
import com.pnf.api.response.CategoryRes;
import com.pnf.api.response.ProductGetRes;
import com.pnf.db.entity.Bookmark;
import com.pnf.db.entity.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product registerProduct(ProductRegisterPostReq productRegisterPostReq) throws IOException;
    Page<ProductGetRes> getListProduct(ProductListGetReq productListGetReq);
    List<ProductGetRes> getRankListProduct();
    ProductGetRes selectProduct(Long id);
    List<CategoryRes> getListAllCategory();
    Page<ProductGetRes> selectExpertProduct(ProductExpertListGetReq productExpertListGetReq);
    Boolean addBookmark(BookmarkAddReq bookmarkAddReq);
    Page<BookmarkListGetRes> getListBookmark(BookmarkListGetReq bookmarkListGetReq);
    Bookmark useBookmark(BookmarkGetReq bookmarkGetReq);
    Boolean deleteBookmark(BookmarkDeleteReq bookmarkDeleteReq);
}
