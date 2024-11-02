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

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void join(MemberDTO memberDto) {

        Member member = Member.builder()
                .name(memberDto.getName())
                .userId(memberDto.getUserId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .build();

        memberRepository.save(member);
    }

    public Map<String, String> handleValidation(Errors errors) {

        Map<String, String> errorMap = new HashMap<>();

        for(FieldError fieldError : errors.getFieldErrors()) {
            errorMap.put(String.format("valid_%s",fieldError.getField())
                    , fieldError.getDefaultMessage());
        }

        return errorMap;
    }
}
