package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Penalty extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Driver driver;

    private boolean objection;

    @Builder
    public Penalty(Long amount,Driver driver){
        this.amount = amount;
        this.driver = driver;
        this.objection = false;
    }

    public void updateObjection(){
        this.objection = true;
    }
}
