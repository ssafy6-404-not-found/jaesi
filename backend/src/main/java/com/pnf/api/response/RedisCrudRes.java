package com.pnf.api.response;

import com.pnf.db.entity.RedisCrud;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RedisCrudRes {
    private Long id;
    private String description;
    private LocalDateTime updatedAt;

    public RedisCrudRes(RedisCrud redisHash) {
        this.id = redisHash.getId();
        this.description = redisHash.getDescription();
        this.updatedAt = redisHash.getUpdatedAt();
    }
}
