package com.project.demo.domain.members.dto.response;

import com.project.demo.domain.whiskeys.dto.WhiskeyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageResponse {

    private String userId;
    private List<WhiskeyDTO> whiskeys;
}
