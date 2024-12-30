package com.project.demo.domain.board.entity;

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
    private Long whiskeyId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate regDate;

    @Column(length = 500)
    private String content;

    @Builder
    public Board(String writer, Long whiskeyId, String title, LocalDate regDate, String content) {
        this.writer = writer;
        this.whiskeyId = whiskeyId;
        this.title = title;
        this.regDate = regDate;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.regDate = LocalDate.now();
        this.content = content;
    }
}
