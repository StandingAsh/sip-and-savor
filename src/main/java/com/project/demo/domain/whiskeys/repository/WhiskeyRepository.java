package com.project.demo.domain.whiskeys.repository;

import com.project.demo.domain.whiskeys.entity.Whiskey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiskeyRepository extends JpaRepository<Whiskey, Long> {
}
