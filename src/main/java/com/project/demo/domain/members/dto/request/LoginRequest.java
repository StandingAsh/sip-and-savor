package com.project.demo.domain.members.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String userId;
    private String password;
}
