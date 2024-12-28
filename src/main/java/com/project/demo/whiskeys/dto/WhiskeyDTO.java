package com.project.demo.whiskeys.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhiskeyDTO {

    private Long id;
    private String name;
    private String imgUrl;
    private String category;
    private String abv;
    private String country;
    private String year;
    private String bottleSize;

    @Builder
    public WhiskeyDTO(Long id, String name, String imgUrl, String category, String abv, String country, String year, String bottleSize) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.abv = abv;
        this.country = country;
        this.year = year;
        this.bottleSize = bottleSize;
    }
}
