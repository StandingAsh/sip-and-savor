package com.project.demo.board.controller;


import com.project.demo.board.dto.BoardDTO;
import com.project.demo.board.entity.Board;
import com.project.demo.board.dto.BoardForm;
import com.project.demo.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@RequestMapping("/boards")
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/writeBoard")
    public String writeBoard(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        return "boards/boardWriteForm";
    }

    @PostMapping("/writeBoard")
    public String writeBoardPro(BoardForm boardForm, Model model) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        BoardDTO boardDTO = BoardDTO.builder()
                .writer(auth.getName())
                .whiskeyId(1L)
                .title(boardForm.getTitle())
                .content(boardForm.getContent())
                .regDate(LocalDate.now())
                .build();

        boardService.save(boardDTO);
        return "redirect:/boards/boardList";
    }

    /* Pageable 인터페이스 통해 해당 페이징 정보 설정 가능
       page = 현재 페이지 인덱스 0 부터 시작
       size = 한 페이지 당 허용되는 데이터 갯수
       sort = 정렬 조건
       direction = 내림차순, 오름차순
       pageable을 매개변수로 설정한 후 위와 같이 요청이 오면 JpaRepository는 그에 해당하는 pageable 객체를 자동으로 만들어준다.
    */

    @GetMapping("/boardList")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boardList = boardService.getBoardList(pageable);

        //페이지블럭 처리
        //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        int nowPage = boardList.getPageable().getPageNumber() + 1;

        //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.
        //-4하는 이유는 현재 페이지 기준으로 앞쪽에 표시할 4개 페이지 포함
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, boardList.getTotalPages());

        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boards/boardList";
    }

    @GetMapping("/boardView/{id}")
    public String boardView(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/boardDetail";
    }

    @GetMapping("/boardDelete")
    public String boardDelete(Long id) {
        boardService.deleteBoardById(id);
        return "redirect:/boards/boardList";
    }

    @GetMapping("/modify/{id}")
    public String boardUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/boardUpdateForm";
    }

    @PostMapping("/boardUpdate/{id}")
    public String boardUpdate(@PathVariable("id") Long id, BoardDTO boardDTO) {
        boardService.updateBoard(id, boardDTO);
        return "redirect:/boards/boardList";
    }
}
