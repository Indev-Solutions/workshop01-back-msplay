package com.indevsolutions.workshop.play.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indevsolutions.workshop.play.domain.Play;

public interface PlayRepository extends JpaRepository<Play, Long> {

	List<Play> findTop5ByUserIdOrderByRegistrationDateDesc(Long userId);
}
