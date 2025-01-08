package com.project.demo.domain.whiskeys.service;

import com.project.demo.domain.whiskeys.dto.WhiskeyDTO;
import com.project.demo.domain.whiskeys.repository.WhiskeyRepository;
import com.project.demo.domain.whiskeys.entity.Whiskey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WhiskeyService {

    private final WhiskeyRepository whiskeyRepository;

    public List<WhiskeyDTO> findAllWhiskeys() {

        List<Whiskey> whiskeyList = whiskeyRepository.findAll();
        List<WhiskeyDTO> dtoList = new ArrayList<>();

        for (Whiskey whiskey : whiskeyList) {
            WhiskeyDTO whiskeyDTO = createWhiskeyDTO(whiskey);
            dtoList.add(whiskeyDTO);
        }

        return dtoList;
    }

    public WhiskeyDTO getWhiskeyById(Long id) {
        return createWhiskeyDTO(whiskeyRepository.findById(id).orElse(null));
    }

    private WhiskeyDTO createWhiskeyDTO(Whiskey whiskey) {

        if (whiskey == null)
            return null;

        return WhiskeyDTO.builder()
                .id(whiskey.getId())
                .abv(whiskey.getAbv())
                .year(whiskey.getYear())
                .bottleSize(whiskey.getBottleSize())
                .category(whiskey.getCategory())
                .country(whiskey.getCountry())
                .imgUrl(whiskey.getImgUrl())
                .name(whiskey.getName())
                .build();
    }
}
