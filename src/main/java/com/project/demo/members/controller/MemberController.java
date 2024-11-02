package com.project.demo.members.controller;

import com.project.demo.members.dto.LoginForm;
import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.dto.MemberForm;
import com.project.demo.members.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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

        MemberDTO memberDto = new MemberDTO();

        memberDto.setName(memberForm.getName());
        memberDto.setUserId(memberForm.getUserId());
        memberDto.setPassword(memberForm.getPassword());
        memberDto.setEmail(memberForm.getEmail());
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
