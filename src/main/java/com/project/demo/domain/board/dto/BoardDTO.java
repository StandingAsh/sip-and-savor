package com.project.demo.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BoardDTO {

    private Long id;
    private String writer;
    private Long whiskeyId;
    private String title;
    private LocalDate regDate;
    private String content;

    @Builder
    public BoardDTO(Long id, String writer, Long whiskeyId, String title, LocalDate regDate, String content) {
        this.id = id;
        this.writer = writer;
        this.whiskeyId = whiskeyId;
        this.title = title;
        this.regDate = regDate;
        this.content = content;
    }
}
