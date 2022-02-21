package com.pnf.db.repository;

import com.pnf.db.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop10ByOrderBySellCountDesc();

    List<Product> findByUserId(Long userId);

    Page<Product> findAllByUserId(Long userId, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);


}
