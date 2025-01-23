package com.project.demo.domain.members.controller;

import com.project.demo.domain.members.dto.request.ChangeInfoRequest;
import com.project.demo.domain.members.dto.request.ChangePasswordRequest;
import com.project.demo.domain.members.dto.request.DeleteRequest;
import com.project.demo.domain.members.dto.response.MemberResponse;
import com.project.demo.domain.members.dto.response.MyPageResponse;
import com.project.demo.domain.members.service.MemberService;
import com.project.demo.domain.members.validator.UserIdValidator;
import com.project.demo.domain.whiskeys.dto.WhiskeyDTO;
import com.project.demo.domain.whiskeys.service.WhiskeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final MemberService memberService;
    private final WhiskeyService whiskeyService;
    private final UserIdValidator userIdValidator;
    private final AuthenticationManager authenticationManager;

    @InitBinder("ChangeInfoRequest")
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(userIdValidator);
    }

    // 로그인 성공
    @GetMapping("/mypage")
    public String myPage(Model model) {

        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            String userId = auth.getName();
            List<Long> whiskeyIds = memberService.getMyWhiskeyIdList(userId);

            List<WhiskeyDTO> whiskeys = new ArrayList<>();
            for (Long whiskeyId : whiskeyIds) {
                whiskeys.add(whiskeyService.getWhiskeyById(whiskeyId));
            }

            MyPageResponse response = new MyPageResponse(userId, whiskeys);
            model.addAttribute("myPage", response);
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

        MemberResponse memberResponse = memberService.findByUserId(auth.getName());
        ChangeInfoRequest changeInfoRequest = new ChangeInfoRequest();

        changeInfoRequest.setName(memberResponse.getName());
        changeInfoRequest.setUserId(memberResponse.getUserId());
        changeInfoRequest.setEmail(memberResponse.getEmail());

        model.addAttribute("changeInfoRequest", changeInfoRequest);
        return "/members/profile/changeInfo";
    }

    @PostMapping("/mypage/change-info")
    public String changeInfo(@Valid ChangeInfoRequest changeInfoRequest, BindingResult result, Model model) {

        // todo: validation 로직 service 로 이동 필요
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            return "/members/profile/changeInfo";
        }

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        try {
            memberService.updateMemberInfo(changeInfoRequest, auth.getName());
            Authentication newAuth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            changeInfoRequest.getUserId(),
                            changeInfoRequest.getPassword())
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
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());
        return "/members/profile/changePassword";
    }

    @PostMapping("/mypage/change-password")
    public String changePassword(@Valid ChangePasswordRequest changePasswordRequest, BindingResult result, Model model) {

        // todo: validation 로직 service 로 이동 필요
        if (result.hasErrors()) {
            log.error(result.getAllErrors().toString());
            return "/members/profile/changePassword";
        }

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        try {
            memberService.updateMemberPassword(changePasswordRequest, auth.getName());
            Authentication newAuth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            auth.getName(),
                            changePasswordRequest.getNewPassword())
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
        model.addAttribute("deleteRequest", new DeleteRequest());
        return "/members/profile/createDeleteForm";
    }

    @PostMapping("/mypage/delete")
    public String createDeleteForm(DeleteRequest deleteRequest, Model model) {

        // 로그인 안돼있는 경우 예외 발생
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();
            deleteRequest.setUserId(auth.getName());

            // 비밀번호 일치하지 않는 경우 예외 발생
            try {
                memberService.delete(deleteRequest);
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
