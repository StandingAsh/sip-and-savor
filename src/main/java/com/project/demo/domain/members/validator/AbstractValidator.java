package com.project.demo.domain.members.validator;

import com.project.demo.domain.members.dto.request.JoinRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
public abstract class AbstractValidator<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    protected abstract void doValidate(final T dto, final Errors errors);
}
