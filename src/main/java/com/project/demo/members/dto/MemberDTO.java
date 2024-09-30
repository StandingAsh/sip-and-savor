package com.project.demo.members.dto;

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

    @Override
    public String toString() {
        return "MemberDTO [" +
                "name=" + name + ", userId=" + userId + ", email=" + email
                + "]";
    }
}
