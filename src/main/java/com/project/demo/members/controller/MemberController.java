package com.project.demo.members.controller;

import com.project.demo.members.dto.LoginForm;
import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.dto.MemberForm;
import com.project.demo.members.service.MemberService;
import com.project.demo.members.validator.UserIdValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final UserIdValidator userIdValidator;

    @InitBinder("MemberForm")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(userIdValidator);
    }

    // 회원 등록
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result, Model model, Errors errors) {

        // 정규식 검사
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        // 중복 아이디 검사
        userIdValidator.validate(memberForm, errors);
        if (errors.hasErrors()) {
            model.addAttribute(errors.getFieldErrors());
            return "members/createMemberForm";
        }

        // 회원가입
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
}
