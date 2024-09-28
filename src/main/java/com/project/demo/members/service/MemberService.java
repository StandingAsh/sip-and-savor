package com.project.demo.members.service;

import com.project.demo.members.entity.Member;
import com.project.demo.members.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;


}
