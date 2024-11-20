package com.project.demo.members.service;

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

    // 유효성 검사
    public Map<String, String> handleValidation(Errors errors) {

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            errorMap.put(String.format("valid_%s", fieldError.getField())
                    , fieldError.getDefaultMessage());
        }

        return errorMap;
    }
}
