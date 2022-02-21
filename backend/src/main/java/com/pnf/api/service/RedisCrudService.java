package com.pnf.api.service;

import com.pnf.api.request.RedisCrudSaveReq;
import com.pnf.api.response.RedisCrudRes;
import com.pnf.db.entity.RedisCrud;
import com.pnf.db.repository.RedisCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RedisCrudService {
    @Autowired
    private RedisCrudRepository repository;

    @Transactional
    public Long save(RedisCrudSaveReq redisCrudSaveReq) {
        return repository.save(redisCrudSaveReq.toRedisHash()).getId();
    }

    public RedisCrudRes get(Long id) {
        RedisCrud redisCrud = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nothing saved. id=" + id));
        return new RedisCrudRes(redisCrud);
    }
}
