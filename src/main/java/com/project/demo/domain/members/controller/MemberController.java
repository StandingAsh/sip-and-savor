package com.project.demo.domain.members.controller;

import com.project.demo.domain.members.dto.JoinRequestDTO;
import com.project.demo.domain.members.dto.LoginRequestDTO;
import com.project.demo.domain.members.service.MemberService;
import com.project.demo.domain.members.validator.UserIdValidator;
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
        model.addAttribute("joinRequestDTO", new JoinRequestDTO());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid JoinRequestDTO joinRequestDTO, BindingResult result, Model model, Errors errors) {

        // todo: Validation 로직 서비스로 리팩토링
        // 정규식 검사
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        // 중복 아이디 검사
        userIdValidator.validate(joinRequestDTO, errors);
        if (errors.hasErrors()) {
            model.addAttribute(errors.getFieldErrors());
            return "members/createMemberForm";
        }

        // 회원가입
        memberService.join(joinRequestDTO);

        return "redirect:/";
    }

    // 로그인
    @GetMapping("/members/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {
        model.addAttribute("loginForm", new LoginRequestDTO());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "members/login";
    }
}
