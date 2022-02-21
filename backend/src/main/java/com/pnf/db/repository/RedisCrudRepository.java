package com.pnf.db.repository;

import com.pnf.db.entity.RedisCrud;
import org.springframework.data.repository.CrudRepository;

public interface RedisCrudRepository extends CrudRepository<RedisCrud, Long> {
}
