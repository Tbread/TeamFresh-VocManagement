package com.teamfresh.voc.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Compensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToOne(mappedBy = "compensation")
    private Voc voc;

    @Column(nullable = false)
    private Long amount;

    @Builder
    public Compensation(Long amount){
        this.amount = amount;
    }
}
