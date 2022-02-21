package com.pnf.db.entity;

import com.fasterxml.jackson.annotation.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity{

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<LiveRoom> liveRooms;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Product> products;

    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private Set<Review> sellsreview;

    @OneToMany(mappedBy = "buyer")
    @JsonManagedReference
    private Set<Review> buysreview;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Bookmark> bookmarks;

    @OneToMany(mappedBy = "seller")
    @JsonManagedReference
    private Set<Orders> sells;

    @OneToMany(mappedBy = "buyer")
    @JsonManagedReference
    private Set<Orders> buys;

    @OneToMany(mappedBy = "sendUser")
    @JsonManagedReference
    private Set<ChatRoom> sends;

    @OneToMany(mappedBy = "receiveUser")
    @JsonManagedReference
    private Set<ChatRoom> receives;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String zipcode;

    private String baseAddress;

    private String detailAddress;

    private String phone;

    @ColumnDefault("0")
    private Integer socialType;

    @Column(length = 500)
    private String profilePhoto;

    @ColumnDefault("false")
    private Boolean emailCertified;

    private String certifiedToken;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
}
