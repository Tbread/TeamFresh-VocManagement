package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Company extends TimeStamped {
    public enum type {
        DELIVERY,
        GOODS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private String contactNum;

    private Long penalty;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Company.type type;


    @Builder
    public Company(String companyName, String contactEmail, String contactNum, Company.type type) {
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.contactNum = contactNum;
        this.type = type;
        this.penalty = 0L;
    }
}
