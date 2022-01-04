package com.teamfresh.voc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Entity
public class Company extends Timestamped {
    public enum type {SALER, DRIVER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String companyName;

    @Column
    @Enumerated(EnumType.STRING)
    private Company.type companyType;
}
