package com.project.demo.domain.whiskeys.repository;

import com.project.demo.domain.whiskeys.entity.Whiskey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhiskeyRepository extends JpaRepository<Whiskey, Long> {

    List<Whiskey> findAllByNameContaining(String keyword);

    List<Whiskey> findAllByCategoryAndNameContaining(String category, String name);
}
