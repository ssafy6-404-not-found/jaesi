package com.pnf.db.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Product extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Bookmark> bookmarks;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Orders> orders;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Review> reviews;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer price;

    @ColumnDefault("0")
    private Long hit;

    @ColumnDefault("0.0")
    private Float rating;

    @ColumnDefault("0")
    private Long sellCount;

    @Column(length = 500)
    private String thumbnail;

    @Column(length = 500)
    private String img;

    @CreatedDate
    private LocalDateTime regTime;

    @Column(nullable = false)
    private Integer productionPeriod;

    @Column(nullable = false)
    private Boolean custom;

}
