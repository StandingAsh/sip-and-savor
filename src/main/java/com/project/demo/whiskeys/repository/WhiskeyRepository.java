package com.project.demo.whiskeys.repository;

import com.project.demo.whiskeys.entity.Whiskey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiskeyRepository extends JpaRepository<Whiskey, Long> {
}
