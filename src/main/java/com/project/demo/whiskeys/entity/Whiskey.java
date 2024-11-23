package com.project.demo.whiskeys.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Whiskey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String abv;

    @Column(nullable = false)
    private String country;

    @Column
    private String year;

    @Column(nullable = false)
    private String bottleSize;

    public Whiskey(String name, String imgUrl, String category, String abv, String country, String year, String bottleSize) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.abv = abv;
        this.country = country;
        this.year = year;
        this.bottleSize = bottleSize;
    }
    
}
