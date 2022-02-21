package com.pnf.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
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
public class Orders extends BaseEntity {

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User seller;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "review_id")
    @JsonManagedReference
    private Review review;

    @Column(nullable = false)
    private Integer price;

    private Integer count;

    @Column(nullable = false)
    private String description;

    private Integer zipcode;

    private String baseAddress;

    private String detailAddress;

    @ColumnDefault("false")
    private Boolean state;

    private String receiverName;

    private String receiverPhone;

    @CreatedDate
    private LocalDateTime regTime;

    private LocalDate dueDate;

    private LocalDate endDate;

    @ColumnDefault("false")
    private Boolean isPayComplete;
}
