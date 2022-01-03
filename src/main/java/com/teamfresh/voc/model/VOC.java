package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Entity
public class VOC extends Timestamped {
    public enum responsibility {DRIVERFAULT, SALERFAULT}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //귀책이 운송사인지, 고객사인지
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VOC.responsibility responsibility;


    //귀책의 상세 내용
    @NotEmpty
    @Column(nullable = false)
    private String faultDetails;

    //종료여부
    @Column(nullable = false)
    private boolean conclude;

    @OneToOne
    @JoinColumn
    private Compensation compensation;


    @Builder
    public VOC(responsibility responsibility, String faultDetails,Compensation compensation) {
        this.responsibility = responsibility;
        this.faultDetails = faultDetails;
        this.compensation = compensation;
        this.conclude = false;
    }
}
