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

    //배상금액
    @Column(nullable = false)
    private Long compensationAmount;

    //귀책의 상세 내용
    @NotEmpty
    @Column(nullable = false)
    private String faultDetails;

    //운송사 귀책일시, 기사의 확인여부 (고객사 귀책일시, default값 true)
    @Column(nullable = false)
    private boolean driversChk;

    //운송사 귀책일시, 기사의 이의제기 여부 (고객사 귀책일시, default값 false)
    @Column(nullable = false)
    private boolean objection;

    //종료여부
    @Column(nullable = false)
    private boolean conclude;

    //운송사 귀책일시, 입력될 기사의 pk
    @Column
    private Long driverId;


    @Builder
    public VOC(responsibility responsibility, Long compensationAmount, String faultDetails,Long driverId) {
        this.responsibility = responsibility;
        this.compensationAmount = compensationAmount;
        this.faultDetails = faultDetails;
        this.objection = false;
        this.conclude = false;
        // 귀책사 여부에 따른 default값 변경
        this.driversChk = !responsibility.toString().equals("DRIVERFAULT");
        this.driverId = driverId;
    }
}
