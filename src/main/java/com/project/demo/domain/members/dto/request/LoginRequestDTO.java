package com.project.demo.domain.members.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    private String userId;
    private String password;
}
