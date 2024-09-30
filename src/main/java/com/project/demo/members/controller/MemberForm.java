package com.project.demo.members.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberForm {

    private String name;
    private String userId;
    private String password;
    private String email;

    @Override
    public String toString() {
        return "MemberForm [" +
                "name=" + name + ", " +
                "userId=" + userId + ", " +
                "password=" + password + ", " +
                "email=" + email
                + "]";
    }
}
