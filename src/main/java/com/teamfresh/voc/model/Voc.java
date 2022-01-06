package com.teamfresh.voc.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Voc extends TimeStamped {
    public enum responsibility {
        DRIVER, SELLER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Voc.responsibility responsibility;

    @Column(nullable = false)
    private String details;

    private boolean checked;

    private boolean objection;

    private Long driverId;

    private Long companyId;

    @JsonBackReference
    @OneToOne
    @JoinColumn
    private Compensation compensation;

    private boolean conclude;

    @Builder
    public Voc(String details, Voc.responsibility responsibility, Compensation compensation,Long driverId,Long companyId) {
        this.responsibility = responsibility;
        this.details = details;
        this.compensation = compensation;
        this.companyId = companyId;
        this.checked = false;
        this.objection = false;
        this.driverId = driverId;
        this.conclude = false;
    }

    public void updateObjection(){
        this.objection = true;
    }

    public void updateChecked(){
        this.checked = true;
    }

    public void updateConclude() {
        this.conclude = true;
    }
}
