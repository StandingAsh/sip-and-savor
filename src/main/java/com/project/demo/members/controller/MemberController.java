package com.project.demo.members.controller;

import com.project.demo.members.dto.LoginForm;
import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.dto.MemberForm;
import com.project.demo.members.service.MemberService;
import com.project.demo.members.validator.UserIdValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final UserIdValidator userIdValidator;

    @InitBinder("MemberForm")
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
            Map<String, String> errorMap = memberService.handleValidation(errors);
            for (String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            return "members/createMemberForm";
        }

        userIdValidator.validate(memberForm, errors);
        if (errors.hasErrors()) {
            model.addAttribute(errors.getFieldErrors());
            return "members/createMemberForm";
        }

        MemberDTO memberDto = MemberDTO.builder()
                .name(memberForm.getName())
                .email(memberForm.getEmail())
                .userId(memberForm.getUserId())
                .password(memberForm.getPassword())
                .build();
        memberService.join(memberDto);

        return "redirect:/";
    }

    // 로그인
    @GetMapping("/members/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "members/login";
    }

    // 로그인 성공
    @GetMapping("/mypage")
    public String myPage(Model model, Principal principal) {


        return "mypage";
    }
}
