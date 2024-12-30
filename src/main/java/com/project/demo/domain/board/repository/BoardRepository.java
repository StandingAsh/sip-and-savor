package com.project.demo.domain.board.repository;

import com.project.demo.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByWhiskeyId(Long id);
}
