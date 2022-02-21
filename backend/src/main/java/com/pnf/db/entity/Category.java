package com.pnf.db.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category extends BaseEntity {

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Set<Product> products;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private Set<LiveRoom> liveRooms;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String bannerImg;

}
