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

    @JsonBackReference
    @OneToOne
    @JoinColumn
    private Compensation compensation;

    @Builder
    public Voc(String details, Voc.responsibility responsibility, Compensation compensation) {
        this.responsibility = responsibility;
        this.details = details;
        this.compensation = compensation;
        this.checked = false;
        this.objection = false;
    }

    public void updateObjection(){
        this.objection = true;
    }

    public void updateChecked(){
        this.checked = true;
    }
}
