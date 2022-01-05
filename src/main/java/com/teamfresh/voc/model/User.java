package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private Long companyId;

    @Column
    private Long ownCompany;

    @Builder
    public User(String username,String password,Long companyId){
        this.username = username;
        this.password = password;
        this.companyId = companyId;
    }

}
