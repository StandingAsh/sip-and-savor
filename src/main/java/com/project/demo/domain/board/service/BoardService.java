package com.project.demo.domain.board.service;

import com.project.demo.domain.board.dto.BoardDTO;
import com.project.demo.domain.board.entity.Board;
import com.project.demo.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Page<Board> getBoardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<BoardDTO> getBoardListByWhiskeyId(Long id, Pageable pageable) {

        List<Board> list = boardRepository.findAllByWhiskeyId(id);
        List<BoardDTO> dtoList = new ArrayList<>();
        for (Board board : list) {
            BoardDTO boardDTO = BoardDTO.fromEntity(board);
            dtoList.add(boardDTO);
        }

        // List to Page
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtoList.size());

        return new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());
    }

    public BoardDTO findBoardById(Long id) throws Exception {
        Board board = boardRepository.findById(id).orElse(null);
        if (board == null) {
            throw new Exception("해당 게시글이 존재하지 않습니다.");
        }
        return BoardDTO.fromEntity(board);
    }

    public Long deleteBoardById(Long id) {
        Long whiskeyId = boardRepository.findById(id).get().getWhiskeyId();
        boardRepository.deleteById(id);
        return whiskeyId;
    }

    @Transactional
    public Long updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 게시글이 존재하지 않습니다. ID: " + id));

        board.update(boardDTO.getTitle(), boardDTO.getContent());
        return board.getWhiskeyId();
    }
}
