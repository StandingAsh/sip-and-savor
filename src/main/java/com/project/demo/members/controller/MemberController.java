package com.project.demo.members.controller;

import com.project.demo.members.dto.LoginForm;
import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.dto.MemberForm;
import com.project.demo.members.service.MemberService;
import com.project.demo.members.validator.UserIdValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final UserIdValidator userIdValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(userIdValidator);
    }

    // 회원 등록
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("memberForm", memberForm);

            Map<String, String> errorMap = memberService.handleValidation(errors);
            for(String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            return "/members/createMemberForm";
        }

        MemberDTO memberDto = MemberDTO.builder()
                .name(memberForm.getName())
                .email(memberForm.getEmail())
                .userId(memberForm.getUserId())
                .password(memberForm.getPassword())
                .build();
        memberService.join(memberDto);

        System.out.println(memberDto);

        return "redirect:/";
    }

    // 로그인
    @GetMapping("/members/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "members/login";
    }

    // 로그인 성공
    @GetMapping("/mypage")
    public String myPage() {
        return "mypage";
    }
}
