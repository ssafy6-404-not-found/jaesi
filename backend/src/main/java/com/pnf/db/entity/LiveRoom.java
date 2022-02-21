package com.pnf.db.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class LiveRoom extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(length = 500)
    private String thumbnail;

    @CreatedDate
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ColumnDefault("0")
    private Long hit;

    @ColumnDefault("0")
    private Long realTimeViewers;

    public void endLive(LocalDateTime endTime){
        this.endTime = endTime;
    }

    public void increaseHit(Long hit){
        this.hit = hit+1;
    }
}
