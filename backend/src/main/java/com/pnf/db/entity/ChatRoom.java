package com.pnf.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom extends BaseEntity{

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User sendUser;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private User receiveUser;
}
