package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn
    private Company company;

    private Long penalty;


    @Builder
    public Driver(Company company){
        this.company = company;
        this.penalty = 0L;
    }

    public void updatePenalty(Penalty penalty){
        this.penalty = this.penalty + penalty.getAmount();
    }

}
