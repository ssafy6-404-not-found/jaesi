package com.pnf.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Expert extends User{

    private String businessNumber;

    private String businessName;

    private String businessType;

    private Float rating;
}
