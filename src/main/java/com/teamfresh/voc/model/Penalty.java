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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Compensation compensation;

    @Builder
    public Penalty(Long amount,Driver driver,Compensation compensation){
        this.amount = amount;
        this.driver = driver;
        this.objection = false;
        this.compensation = compensation;
    }

    public void updateObjection(){
        this.objection = true;
    }
}
