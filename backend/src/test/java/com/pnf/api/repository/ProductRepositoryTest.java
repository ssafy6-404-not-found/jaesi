package com.pnf.api.repository;

import com.pnf.db.entity.Product;
import com.pnf.db.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    public void 전문가서비스목록_반환() {
        Long sellerId = 1L;
        Pageable pageable = PageRequest.of(0,3);

        Page<Product> orders = repository.findAllByUserId(sellerId,pageable);
        System.out.println(orders.getContent());
    }

}
