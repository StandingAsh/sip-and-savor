package com.project.demo.members.validator;

import com.project.demo.members.dto.MemberDTO;
import com.project.demo.members.dto.MemberForm;
import com.project.demo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class UserIdValidator extends AbstractValidator<MemberForm> {

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberForm form, Errors errors) {
        if(memberRepository.existsByUserId(form.getUserId())) {
            errors.rejectValue("userId", "user.id.exists"
                    , "이미 존재하는 아이디입니다.");
        }
    }
}
