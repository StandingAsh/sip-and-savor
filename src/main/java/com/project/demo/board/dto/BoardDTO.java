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

    public static BoardDTO fromEntity(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .writer(board.getWriter())
                .whiskeyId(board.getWhiskeyId())
                .title(board.getTitle())
                .regDate(board.getRegDate())
                .content(board.getContent())
                .build();
    }

    // DTO -> Entity 변환
    public Board toEntity() {
        return Board.builder()
                .writer(this.writer)
                .whiskeyId(this.whiskeyId)
                .title(this.title)
                .regDate(this.regDate)
                .content(this.content)
                .build();
    }
}
