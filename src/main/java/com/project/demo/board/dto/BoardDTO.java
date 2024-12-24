package com.project.demo.board.dto;

import com.project.demo.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BoardDTO {
    private Long id;
    private String writer;
    private String email;
    private String title;
    private LocalDate regDate;
    private String content;

    @Builder
    public BoardDTO(String writer, String email, String title, LocalDate regDate, String content) {
        this.writer = writer;
        this.email = email;
        this.title = title;
        this.regDate = regDate;
        this.content = content;
    }

    public static BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .writer(board.getWriter())
                .email(board.getEmail())
                .title(board.getTitle())
                .regDate(board.getRegDate())
                .content(board.getContent())
                .build();
    }

    // DTO -> Entity 변환
    public Board toEntity() {
        return Board.builder()
                .writer(this.writer)
                .email(this.email)
                .title(this.title)
                .regDate(this.regDate)
                .content(this.content)
                .build();
    }
}
