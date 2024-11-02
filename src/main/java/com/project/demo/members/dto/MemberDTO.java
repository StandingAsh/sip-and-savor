package com.project.demo.members.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {

    private Long id;
    private String name;
    private String userId;
    private String password;
    private String email;

    @Builder
    public MemberDTO(String name, String userId, String password, String email) {

        this.name = name;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "MemberDTO [" +
                "name=" + name + ", userId=" + userId + ", email=" + email
                + "]";
    }
}
