package com.project.demo.domain.whiskeys.service;

import com.project.demo.domain.whiskeys.dto.WhiskeyDTO;
import com.project.demo.domain.whiskeys.repository.WhiskeyRepository;
import com.project.demo.domain.whiskeys.entity.Whiskey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WhiskeyService {

    private final WhiskeyRepository whiskeyRepository;

    public List<WhiskeyDTO> findWhiskeys(String keyword) {
        return whiskeyRepository.findAllByNameContaining(keyword).stream()
                .map(this::createWhiskeyDTO).toList();
    }

    public List<WhiskeyDTO> findWhiskeysByCategory(String category, String keyword) {
        return whiskeyRepository.findAllByCategoryAndNameContaining(category, keyword).stream()
                .map(this::createWhiskeyDTO).toList();
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
