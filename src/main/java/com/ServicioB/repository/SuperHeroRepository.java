package com.ServicioB.repository;


import com.ServicioB.entities.repository_entities.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {
    SuperHero findByName(String superHeroName);

}
