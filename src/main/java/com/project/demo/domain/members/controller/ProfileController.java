package com.project.demo.domain.members.controller;

import com.project.demo.domain.members.dto.*;
import com.project.demo.domain.members.service.MemberService;
import com.project.demo.domain.members.validator.UserIdValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final MemberService memberService;
    private final UserIdValidator userIdValidator;
    private final AuthenticationManager authenticationManager;

    @InitBinder("ChangeInfoRequestDTO")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(userIdValidator);
    }

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

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        MemberResponseDTO memberResponseDTO = memberService.findByUserId(auth.getName());
        ChangeInfoRequestDTO changeInfoRequestDTO = new ChangeInfoRequestDTO();

        changeInfoRequestDTO.setName(memberResponseDTO.getName());
        changeInfoRequestDTO.setUserId(memberResponseDTO.getUserId());
        changeInfoRequestDTO.setEmail(memberResponseDTO.getEmail());

        model.addAttribute("changeInfoRequestDTO", changeInfoRequestDTO);
        return "/members/profile/changeInfo";
    }

    @PostMapping("/mypage/change-info")
    public String changeInfo(@Valid ChangeInfoRequestDTO changeInfoRequestDTO, BindingResult result, Model model) {

        // todo: validation 로직 service 로 이동 필요
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            return "/members/profile/changeInfo";
        }

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        try {
            memberService.updateMemberInfo(changeInfoRequestDTO, auth.getName());
            Authentication newAuth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            changeInfoRequestDTO.getUserId(),
                            changeInfoRequestDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        } catch (Exception e) {
            log.error(e.toString());
            model.addAttribute("exception", e.getMessage());
            return "/members/profile/changeInfo";
        }

        return "redirect:/mypage";
    }

    // 비밀번호 변경
    @GetMapping("/mypage/change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordRequestDTO", new ChangePasswordRequestDTO());
        return "/members/profile/changePassword";
    }

    @PostMapping("/mypage/change-password")
    public String changePassword(@Valid ChangePasswordRequestDTO changePasswordRequestDTO, BindingResult result, Model model) {

        // todo: validation 로직 service 로 이동 필요
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            return "/members/profile/changePassword";
        }

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        try {
            memberService.updateMemberPassword(changePasswordRequestDTO, auth.getName());
            Authentication newAuth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getName(),
                            changePasswordRequestDTO.getNewPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        } catch (Exception e) {
            log.error(e.toString());
            model.addAttribute("exception", e.getMessage());

            return "/members/profile/changePassword";
        }

        return "redirect:/mypage/change-info";
    }

    // 회원 탈퇴
    @GetMapping("/mypage/delete")
    public String createDeleteForm(Model model) {
        model.addAttribute("deleteRequestDTO", new DeleteRequestDTO());
        return "/members/profile/createDeleteForm";
    }

    @PostMapping("/mypage/delete")
    public String createDeleteForm(DeleteRequestDTO deleteRequestDTO, Model model) {

        // 로그인 안돼있는 경우 예외 발생
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();
            deleteRequestDTO.setUserId(auth.getName());

            // 비밀번호 일치하지 않는 경우 예외 발생
            try {
                memberService.delete(deleteRequestDTO);
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
