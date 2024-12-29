package com.project.demo.board.repository;

import com.project.demo.board.entity.Board;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByWhiskeyId(Long id);
}
