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
    private LocalDate reg_date;
    private String content;

    @Builder
    public BoardDTO(String writer, String email, String title, LocalDate reg_date, String content) {
        this.writer = writer;
        this.email = email;
        this.title = title;
        this.reg_date = reg_date;
        this.content = content;
    }

    public static BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .writer(board.getWriter())
                .email(board.getEmail())
                .title(board.getTitle())
                .reg_date(board.getReg_date())
                .content(board.getContent())
                .build();
    }

    // DTO -> Entity 변환
    public Board toEntity() {
        return Board.builder()
                .writer(this.writer)
                .email(this.email)
                .title(this.title)
                .reg_date(LocalDate.now())
                .content(this.content)
                .build();
    }
}
