package com.teamfresh.voc.model;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Compensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private boolean checked;

    @Column(nullable = false)
    private boolean objection;

    @Column(nullable = false)
    private Long driverId;

    @OneToOne(mappedBy = "compensation")
    @JoinColumn
    private VOC voc;

    @Builder
    public Compensation(Long amount,Long driverId){
        this.amount = amount;
        this.driverId = driverId;
        this.checked = false;
        this.objection = false;
    }
}
