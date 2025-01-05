package com.project.demo.domain.members.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteRequestDTO {

    private String userId;
    private String password;
}
