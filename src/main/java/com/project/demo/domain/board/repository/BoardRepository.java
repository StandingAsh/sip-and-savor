package com.project.demo.domain.board.repository;

import com.project.demo.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByWhiskeyId(Long id, Pageable pageable);

    Page<Board> findAllByWhiskeyIdAndWriter_UserId(Long id, String writer, Pageable pageable);
}
