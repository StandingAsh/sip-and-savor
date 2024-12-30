package com.project.demo.domain.members.validator;

import com.project.demo.domain.members.dto.MemberForm;
import com.project.demo.domain.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserIdValidator extends AbstractValidator<MemberForm> {

    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberForm form, Errors errors) {
        if(memberRepository.existsByUserId(form.getUserId())) {
            errors.rejectValue("userId", "user.id.exists"
                    , "이미 존재하는 아이디입니다.");
            log.error(Objects.requireNonNull(errors.getFieldError())
                    .getDefaultMessage());
        }
    }
}
