package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn
    private Company company;

    @OneToOne
    @JoinColumn
    private Driver driver;

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void updateDriver(Driver driver) {
        this.driver = driver;
    }

    public void updateCompany(Company company) {
        this.company = company;
    }
}
