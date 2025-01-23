package com.project.demo.domain.members.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteRequest {

    private String userId;
    private String password;
}
