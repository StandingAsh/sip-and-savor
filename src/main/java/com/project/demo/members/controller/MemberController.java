package com.project.demo.members.controller;

import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.entity.Member;
import com.project.demo.members.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String create(MemberForm memberForm) {
        System.out.println("memberForm = " + memberForm.toString());

        MemberDTO memberDto = new MemberDTO();

        memberDto.setName(memberForm.getName());
        memberDto.setUserId(memberForm.getUserId());
        memberDto.setPassword(memberForm.getPassword());
        memberDto.setEmail(memberForm.getEmail());
        memberService.join(memberDto);

        System.out.println(memberDto);

        return "redirect:/";
    }
}