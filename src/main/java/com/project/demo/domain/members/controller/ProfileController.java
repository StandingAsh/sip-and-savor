package com.project.demo.domain.members.controller;

import com.project.demo.domain.members.dto.ChangeInfoForm;
import com.project.demo.domain.members.dto.DeleteForm;
import com.project.demo.domain.members.dto.UserDetailsAdapter;
import com.project.demo.domain.members.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    // 회원 정보 수정
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

    // 회원 탈퇴
    @GetMapping("/mypage/delete")
    public String createDeleteForm(Model model) {
        model.addAttribute("deleteForm", new DeleteForm());
        return "/members/profile/createDeleteForm";
    }

    @PostMapping("/mypage/delete")
    public String createDeleteForm(DeleteForm deleteForm, Model model) {

        // 로그인 안돼있는 경우 예외 발생
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();
            deleteForm.setUserId(auth.getName());

            // 비밀번호 일치하지 않는 경우 예외 발생
            try {
                memberService.delete(deleteForm);
                SecurityContextHolder.getContext().setAuthentication(null);
            } catch (Exception e) {
                log.error(e.getMessage());
                model.addAttribute("error", e.getMessage());
                return "/members/profile/createDeleteForm";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            model.addAttribute("exception", e.getMessage());
            return "/members/profile/createDeleteForm";
        }

        return "redirect:/";
    }
}
