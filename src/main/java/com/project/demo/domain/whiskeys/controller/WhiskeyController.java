package com.project.demo.domain.whiskeys.controller;

import com.project.demo.domain.board.dto.BoardDTO;
import com.project.demo.domain.board.service.BoardService;
import com.project.demo.domain.whiskeys.dto.WhiskeyDTO;
import com.project.demo.domain.whiskeys.service.WhiskeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WhiskeyController {

    private final WhiskeyService whiskeyService;
    private final BoardService boardService;

    @GetMapping("/whiskeys")
    public String display(Model model,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword,
                          @RequestParam(value = "category", required = false) String category) {

        List<WhiskeyDTO> whiskeyList;
        if (category == null || category.isEmpty()) {
            whiskeyList = whiskeyService.findWhiskeys(keyword);
        } else {
            whiskeyList = whiskeyService.findWhiskeysByCategory(category, keyword);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("whiskeyList", whiskeyList);

        List<String> categories = new ArrayList<>();
        categories.add("싱글몰트");
        categories.add("블렌드");
        categories.add("버번");
        categories.add("라이");
        model.addAttribute("categories", categories);
        model.addAttribute("category", category);

        return "whiskeys/whiskeyList";
    }

    @GetMapping("/whiskeys/{id}")
    public String displayDetails(@PathVariable(name = "id") Long whiskeyId,
                                 @RequestParam(defaultValue = "false") Boolean checked,
                                 @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model) {

        WhiskeyDTO whiskey = whiskeyService.getWhiskeyById(whiskeyId);
        model.addAttribute("whiskey", whiskey);

        // 체크박스 체크 여부로 보드 리스트 다르게 받아오기
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<BoardDTO> boardList = checked
                ? boardService.getBoardListByWhiskeyIdAndWriter(whiskeyId, userName, pageable)
                : boardService.getBoardListByWhiskeyId(whiskeyId, pageable);

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
        model.addAttribute("checked", checked);

        return "whiskeys/whiskeyDetail";
    }

    @GetMapping("/whiskeys/about")
    public String displayAbout() {
        return "about";
    }
}
