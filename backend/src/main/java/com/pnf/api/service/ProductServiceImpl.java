package com.pnf.api.service;

import com.pnf.api.request.*;
import com.pnf.api.response.BookmarkListGetRes;
import com.pnf.api.response.CategoryRes;
import com.pnf.api.response.ProductGetRes;
import com.pnf.db.entity.Bookmark;
import com.pnf.db.entity.Category;
import com.pnf.db.entity.Product;
import com.pnf.db.entity.User;
import com.pnf.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    BookmarkRepository bookmarkRepository;


    @Override
    public Page<ProductGetRes> getListProduct(ProductListGetReq productListGetReq) {

        Sort sort = (productListGetReq.getSortType() == 0) ? Sort.by("regTime").descending().and(Sort.by("hit").descending()) : Sort.by("hit").descending().and(Sort.by("regTime").descending());
        Pageable pageable = PageRequest.of(productListGetReq.getCurrPage() - 1, productListGetReq.getPerPage(), sort);
        Page<ProductGetRes> productRes;

        if (productListGetReq.getCategory() == 0)   //전체 목록
            productRes = productRepository.findAll(pageable).map(ProductGetRes::from);
        else    //카테고리 별 목록
            productRes = productRepository.findByCategoryId(Long.valueOf(productListGetReq.getCategory()), pageable).map(ProductGetRes::from);

        return productRes;
    }

    @Override
    public List<ProductGetRes> getRankListProduct() {
        List<Product> products = productRepository.findTop10ByOrderBySellCountDesc();
        List<ProductGetRes> productRes = new ArrayList<>();
        for (Product p : products)
            productRes.add(ProductGetRes.from(p));

        return productRes;
    }

    @Override
    public ProductGetRes selectProduct(Long productId) {

//        Review review = reviewRepository.findAll();
        Product product = productRepository.findById(productId).get();

        ProductGetRes productRes = ProductGetRes.builder()
                .productId(product.getId())
                .user(product.getUser().getId())
                .userNickname(product.getUser().getNickname())
                .category(product.getCategory().getId())
                .reviews(product.getReviews())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .hit(product.getHit())
                .rating(product.getRating())
                .sell_count(product.getSellCount())
                .thumbnail(product.getThumbnail())
                .img(product.getImg())
                .productionPeriod(product.getProductionPeriod())
                .custom(product.getCustom())
                .build();
        return productRes;
    }

    @Override
    public List<CategoryRes> getListAllCategory() {
        List<CategoryRes> categoryRes = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();

        for (Category c : categories) {
            categoryRes.add(CategoryRes.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .bannerImg(c.getBannerImg())
                    .build());
        }
        return categoryRes;
    }

    @Override
    public Page<ProductGetRes> selectExpertProduct(ProductExpertListGetReq productExpertListGetReq) {
        Long sellerId = productExpertListGetReq.getSellerId();
        Pageable pageable = PageRequest.of(productExpertListGetReq.getCurrPage() - 1, productExpertListGetReq.getPerPage());
        Page<ProductGetRes> products = productRepository.findAllByUserId(sellerId, pageable).map(ProductGetRes::from);

        return products;
    }

    @Override
    public Boolean addBookmark(BookmarkAddReq bookmarkAddReq) {

        User user = userRepository.findById(bookmarkAddReq.getUserId()).get();
        Product product = productRepository.findById(bookmarkAddReq.getProductId()).get();

        Optional<Bookmark> bookmark = bookmarkRepository.findByUserIdAndProductId(bookmarkAddReq.getUserId(), bookmarkAddReq.getProductId());

        if(bookmark.isPresent()){
            return false;
        }

        bookmarkRepository.save(Bookmark.builder()
                .user(user)
                .product(product)
                .build());

        return true;
    }

    @Override
    public Page<BookmarkListGetRes> getListBookmark(BookmarkListGetReq bookmarkListGetReq) {
        Long userId = bookmarkListGetReq.getUserId();
        Pageable pageable = PageRequest.of(bookmarkListGetReq.getCurrPage()-1, bookmarkListGetReq.getPerPage());
        Page<BookmarkListGetRes> bookmarkGetRes = bookmarkRepository.findAllByUserId(userId, pageable).map(BookmarkListGetRes::from);

        if(bookmarkGetRes == null) {
            return null;
        }

        return bookmarkGetRes;
    }

    @Override
    public Bookmark useBookmark(BookmarkGetReq bookmarkGetReq) {

        Optional<Bookmark> optBookmark = bookmarkRepository.findByUserIdAndProductId(bookmarkGetReq.getUserId(), bookmarkGetReq.getProductId());
        if (optBookmark.isPresent())
            return optBookmark.get();
        else
            return null;
    }

    @Override
    public Boolean deleteBookmark(BookmarkDeleteReq bookmarkDeleteReq) {
        User user = userRepository.findById(bookmarkDeleteReq.getUserId()).get();
        if (user == null) {
            return false;
        } else {
            bookmarkRepository.deleteBookmarkByUserIdAndProductId(user.getId(), bookmarkDeleteReq.getProductId());
            return true;
        }

    }

    @Override
    public Product registerProduct(ProductRegisterPostReq productRegisterPostReq) throws IOException {
        Category category = categoryRepository.findById(productRegisterPostReq.getCategory()).get();
        User user = userRepository.findById(productRegisterPostReq.getUser()).get();
        Product product = Product.builder()
                .user(user)
                .title(productRegisterPostReq.getTitle())
                .content(productRegisterPostReq.getContent())
                .category(category)
                .price(productRegisterPostReq.getPrice())
                .img(productRegisterPostReq.getImg())
                .custom(productRegisterPostReq.getCustom())
                .productionPeriod(productRegisterPostReq.getProductionPeriod())
                .build();
        productRepository.save(product);

        return product;
    }

}
