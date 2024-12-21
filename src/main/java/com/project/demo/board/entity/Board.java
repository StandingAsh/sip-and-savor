package com.project.demo.board.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate reg_date;

    @Column(length = 500)
    private String content;

    @Builder
    public Board(String writer, String email, String title, LocalDate reg_date, String content) {
        this.writer = writer;
        this.email = email;
        this.title = title;
        this.reg_date = reg_date;
        this.content = content;
    }


    public void update(String writer, String email, String title, LocalDate reg_date, String content) {
        this.writer = writer;
        this.email = email;
        this.title = title;
        this.reg_date = LocalDate.now();
        this.content = content;
    }

}
