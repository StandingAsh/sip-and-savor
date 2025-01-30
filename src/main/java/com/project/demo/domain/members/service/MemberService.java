package com.project.demo.domain.members.service;

import com.project.demo.domain.board.entity.Board;
import com.project.demo.domain.members.dto.request.ChangeInfoRequest;
import com.project.demo.domain.members.dto.request.ChangePasswordRequest;
import com.project.demo.domain.members.dto.request.DeleteRequest;
import com.project.demo.domain.members.dto.request.JoinRequest;
import com.project.demo.domain.members.dto.response.MemberResponse;
import com.project.demo.domain.members.entity.Member;
import com.project.demo.domain.members.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public void join(JoinRequest joinRequest) throws Exception {
        if(memberRepository.existsByUserId(joinRequest.getUserId()))
            throw new Exception("이미 존재하는 아이디입니다.");

        memberRepository.save(createMemberEntity(joinRequest));
    }

    // 회원탈퇴
    public void delete(DeleteRequest deleteRequest) throws Exception {

        // todo: 비밀번호 검사, 예외 던지기
        Member member = memberRepository.findByUserId(deleteRequest.getUserId());
        if (member == null)
            throw new Exception("회원 정보를 찾을 수 없습니다.");

        if (!passwordEncoder.matches(deleteRequest.getPassword(), member.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        member.removeBoards();
        memberRepository.delete(member);
    }

    // 회원 정보 변경
    @Transactional
    public void updateMemberInfo(ChangeInfoRequest form, String userId) throws Exception {

        Member member = memberRepository.findByUserId(userId);
        if (!passwordEncoder.matches(form.getPassword(), member.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        if (memberRepository.existsByUserId(form.getUserId())
                && !form.getUserId().equals(userId))
            throw new Exception("이미 존재하는 아이디입니다.");

        member.updateInfo(form.getName(), form.getUserId(), form.getEmail());
    }

    // 비밀번호 변경
    @Transactional
    public void updateMemberPassword(ChangePasswordRequest form, String userId) throws Exception {

        if (!memberRepository.existsByUserId(userId)) {
            log.info("회원정보를 찾을 수 없습니다.");
            throw new Exception("회원정보를 찾을 수 없습니다.");
        } else if (form.getNewPassword().equals(form.getOldPassword())) {
            log.info("비밀번호가 기존 비밀번호와 동일합니다.");
            throw new Exception("비밀번호가 기존 비밀번호와 동일합니다.");
        }

        Member member = memberRepository.findByUserId(userId);

        if (!passwordEncoder.matches(form.getOldPassword(), member.getPassword())) {
            log.info("기존 비밀번호가 일치하지 않습니다.");
            throw new Exception("기존 비밀번호가 일치하지 않습니다.");
        }

        if (!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
            log.info("새 비밀번호가 일치하지 않습니다.");
            throw new Exception("새 비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder.encode(form.getNewPassword()));
    }

    // 회원 아이디로 조회
    public MemberResponse findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId);
        return createResponseDTO(member);
    }

    // 회원 목록 조회
    public List<MemberResponse> findAllMembers() {

        List<Member> members = memberRepository.findAll();
        List<MemberResponse> memberResponses = new ArrayList<>();

        for (Member member : members) {
            MemberResponse dto = createResponseDTO(member);
            memberResponses.add(dto);
        }

        return memberResponses;
    }

    // Whiskey ID 반환
    public List<Long> getMyWhiskeyIdList(String userId) {
        List<Long> whiskeyIds = new ArrayList<>();
        for (Board board : memberRepository.findByUserId(userId).getBoardList()) {
            Long id = board.getWhiskeyId();
            if (!whiskeyIds.contains(id))
                whiskeyIds.add(id);
        }

        return whiskeyIds;
    }

    // Utility Methods
    private Member createMemberEntity(JoinRequest memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .userId(memberDto.getUserId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .build();
    }

    private static MemberResponse createResponseDTO(Member member) {
        return MemberResponse.builder()
                .name(member.getName())
                .userId(member.getUserId())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();
    }
}
