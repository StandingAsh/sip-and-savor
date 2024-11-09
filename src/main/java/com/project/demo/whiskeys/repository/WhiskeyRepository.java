package com.project.demo.whiskeys.repository;

import com.project.demo.whiskeys.entity.WhiskeyList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiskeyRepository extends JpaRepository<WhiskeyList, Long> {
}
