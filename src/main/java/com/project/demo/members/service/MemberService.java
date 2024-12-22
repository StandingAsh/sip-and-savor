package com.project.demo.members.service;

import com.project.demo.members.dto.DeleteForm;
import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.entity.Member;
import com.project.demo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
