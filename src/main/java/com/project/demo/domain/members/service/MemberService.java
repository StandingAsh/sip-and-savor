package com.project.demo.domain.members.service;

import com.project.demo.domain.members.dto.ChangeInfoForm;
import com.project.demo.domain.members.dto.ChangePasswordForm;
import com.project.demo.domain.members.dto.DeleteForm;
import com.project.demo.domain.members.entity.Member;
import com.project.demo.domain.members.repository.MemberRepository;
import com.project.demo.domain.members.dto.MemberDTO;
import com.project.demo.domain.members.validator.UserIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserIdValidator userIdValidator;

    // 회원가입
    public void join(MemberDTO memberDto) {

        Member member = Member.builder()
                .name(memberDto.getName())
                .userId(memberDto.getUserId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .build();

        memberRepository.save(member);
    }

    // 회원탈퇴
    public void delete(DeleteForm deleteForm) throws Exception {

        // todo: 비밀번호 검사, 예외 던지기
        Member member = memberRepository.findByUserId(deleteForm.getUserId());
        if (member == null)
            throw new Exception("회원 정보를 찾을 수 없습니다.");

        if (!passwordEncoder.matches(deleteForm.getPassword(), member.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        memberRepository.delete(member);
    }

    public void updateMemberInfo(ChangeInfoForm form, String userId) throws Exception {

        Member member = memberRepository.findByUserId(userId);
        if (memberRepository.existsByUserId(form.getUserId()))
            throw new Exception("이미 존재하는 아이디입니다.");

        member.updateInfo(form.getName(), form.getUserId(), form.getEmail());
    }

    public void updateMemberPassword(ChangePasswordForm form, String userId) throws Exception {

        Member member = memberRepository.findByUserId(userId);
        if(!passwordEncoder.matches(form.getOldPassword(), member.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        if(!form.getNewPassword().equals(form.getNewPasswordConfirm()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        member.updatePassword(form.getNewPassword());
    }

    // 회원 아이디로 조회
    public MemberDTO findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId);
        return MemberDTO.builder()
                .name(member.getName())
                .userId(member.getUserId())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
    }

    // 회원 목록 조회
    public List<MemberDTO> findAllMembers() {

        List<Member> members = memberRepository.findAll();
        List<MemberDTO> memberDTOs = new ArrayList<>();

        for (Member member : members) {
            MemberDTO dto = MemberDTO.builder()
                    .name(member.getName())
                    .userId(member.getUserId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .build();
            memberDTOs.add(dto);
        }

        return memberDTOs;
    }
}
