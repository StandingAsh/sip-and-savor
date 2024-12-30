package com.project.demo.domain.members.service;

import com.project.demo.domain.members.dto.UserDetailsAdapter;
import com.project.demo.domain.members.entity.Member;
import com.project.demo.domain.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsAdapterService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUserId(username);
        if (member == null)
            throw new UsernameNotFoundException(username);

        return new UserDetailsAdapter(member);
    }
}
