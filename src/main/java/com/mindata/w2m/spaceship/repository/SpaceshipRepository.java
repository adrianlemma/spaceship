package com.mindata.w2m.spaceship.repository;

import com.mindata.w2m.spaceship.model.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

    Page<Spaceship> findBySpaceshipNameContainingIgnoreCase(String spaceshipName, Pageable pageable);

}