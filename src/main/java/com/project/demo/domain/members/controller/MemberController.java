package com.project.demo.domain.members.controller;

import com.project.demo.domain.members.dto.request.JoinRequest;
import com.project.demo.domain.members.dto.request.LoginRequest;
import com.project.demo.domain.members.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 등록
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid JoinRequest joinRequest, BindingResult result, Model model, Errors errors) {

        // 정규식 검사
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        // 회원가입
        try {
            memberService.join(joinRequest);
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("userIdDuplicated", e.getMessage());
            return "members/createMemberForm";
        }

        return "redirect:/";
    }

    // 로그인
    @GetMapping("/members/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        model.addAttribute("loginForm", new LoginRequest());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "members/login";
    }
}
