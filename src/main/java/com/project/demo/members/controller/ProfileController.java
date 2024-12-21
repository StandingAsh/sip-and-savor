package com.project.demo.members.controller;

import com.project.demo.members.dto.ChangeInfoForm;
import com.project.demo.members.dto.DeleteForm;
import com.project.demo.members.dto.UserDetailsAdapter;
import com.project.demo.members.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final MemberService memberService;

    // 로그인 성공
    @GetMapping("/mypage")
    public String myPage(Model model) {
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            UserDetailsAdapter user = (UserDetailsAdapter) auth.getPrincipal();
            model.addAttribute("user", user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "/members/profile/mypage";
    }

    @GetMapping("/mypage/change-info")
    public String changeInfo(Model model) {
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            UserDetailsAdapter user = (UserDetailsAdapter) auth.getPrincipal();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        model.addAttribute(new ChangeInfoForm());
        return "/members/profile/changeInfo";
    }

    @PostMapping("/mypage/change-info")
    public String changeInfo(@Valid ChangeInfoForm form, Model model) {
        return "/members/profile/changeInfo";
    }

    @GetMapping("/mypage/createDeleteForm")
    public String createDeleteForm(Model model) {
        model.addAttribute("deleteForm", new DeleteForm());
        return "/members/profile/createDeleteForm";
    }

    @PostMapping("/mypage/createDeleteForm")
    public String createDeleteForm(DeleteForm deleteForm, Model model) {

        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();
            deleteForm.setUserId(auth.getName());



            memberService.delete(deleteForm);
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "/members/profile/createDeleteForm";
        }

        return "redirect:/";
    }
}
