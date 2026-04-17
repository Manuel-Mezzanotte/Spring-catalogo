package com.example.catalogoanime.repository;

import com.example.catalogoanime.model.Anime;
import com.example.catalogoanime.model.AppUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByUserOrderByTitoloAsc(AppUser user); 
    List<Anime> findByUser(AppUser user, Sort sort);
    List<Anime> findByUserAndTitoloContainingIgnoreCase(AppUser user, String titolo, Sort sort);
}
