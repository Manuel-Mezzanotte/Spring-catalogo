package com.example.catalogoanime.repository;

import com.example.catalogoanime.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByTitoloContainingIgnoreCase(String titolo);
}
