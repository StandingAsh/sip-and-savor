package com.project.demo.whiskeys.controller;

import com.project.demo.board.dto.BoardDTO;
import com.project.demo.board.service.BoardService;
import com.project.demo.whiskeys.entity.Whiskey;
import com.project.demo.whiskeys.service.WhiskeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
public class WhiskeyController {

    @Autowired
    private WhiskeyService whiskeyService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/whiskeys")
    public String display(Model model) {

        List<Whiskey> whiskeyList = whiskeyService.findAllWhiskeys();
        model.addAttribute("whiskeyList", whiskeyList);

        return "whiskeys/whiskeyList";
    }

    @GetMapping("/whiskeys/{id}")
    public String displayDetails(@PathVariable(name = "id") Long id,
                                 @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model) {

        Whiskey whiskey = whiskeyService.getWhiskeyById(id);
        model.addAttribute("whiskey", whiskey);

        Page<BoardDTO> boardList = boardService.getBoardListByWhiskeyId(whiskey, pageable);

        // 페이지블럭 처리
        // 1을 더해주는 이유: Pageable 은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 함
        int nowPage = boardList.getPageable().getPageNumber() + 1;

        // -1 값이 들어가는 것을 막기 위해 max 값으로 두 개의 값을 넣고 더 큰 값을 넣어줌
        // -4 하는 이유는 현재 페이지 기준으로 앞쪽에 표시할 4개 페이지 포함
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, boardList.getTotalPages());
        if (endPage == 0)
            endPage = 1;

        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "whiskeys/whiskeyDetail";
    }
}
