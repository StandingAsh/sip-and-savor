package com.project.demo.members.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @Builder
    public Member(String name, String userId, String password, String email) {

        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }
}
