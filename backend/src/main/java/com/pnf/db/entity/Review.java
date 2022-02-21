package com.pnf.db.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.CreatedDate;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User seller;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User buyer;

    @OneToOne(mappedBy = "review")
    @JsonBackReference
    private Orders orders;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDateTime regTime;

    @Column(length = 500)
    private String thumbnail;

    @Column(length = 500)
    private String img;

}
