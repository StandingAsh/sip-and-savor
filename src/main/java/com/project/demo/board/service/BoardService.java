package com.project.demo.board.service;

import com.project.demo.board.dto.BoardDTO;
import com.project.demo.board.entity.Board;
import com.project.demo.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        boardRepository.save(board);
    }

    //기존 List<Board>값으로 넘어가지만 페이징 설정을 해주면 Page<Board>로 넘어간다.
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    public void deleteBoardById(Long id) {boardRepository.deleteById(id);}

    @Transactional
    public void updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. ID: " + id));
        board.update(boardDTO.getWriter(),boardDTO.getEmail(),boardDTO.getTitle(),boardDTO.getReg_date(), boardDTO.getContent());

    }





}
