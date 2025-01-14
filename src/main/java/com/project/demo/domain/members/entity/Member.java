package com.project.demo.domain.members.entity;

import com.project.demo.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "writer", orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public Member(String name, String userId, String password, String email) {

        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    public void updateInfo(String name, String userId, String email) {
        this.name = name;
        this.userId = userId;
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void addBoard(Board board) {
        boardList.add(board);
    }

    public void removeBoard(Board board) {
        boardList.remove(board);
    }

    public void removeBoards() {
        boardList.clear();
    }
}
