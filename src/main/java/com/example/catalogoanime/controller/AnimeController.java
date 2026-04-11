package com.example.catalogoanime.controller;

import com.example.catalogoanime.model.Anime;
import com.example.catalogoanime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeRepository animeRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Anime> animes = animeRepository.findAll();
        model.addAttribute("animes", animes);
        return "home";
    }

    @PostMapping("/api/anime/create")
    public String createAnime(
            @RequestParam String titolo,
            @RequestParam String coverUrl,
            @RequestParam(required = false) String descrizione,
            @RequestParam(required = false) Integer annoUscita,
            @RequestParam(required = false) String studio,
            @RequestParam(required = false) String genere,
            @RequestParam(required = false) Integer episodi,
            @RequestParam(required = false) Boolean inCorso
    ) {
        Anime anime = new Anime();
        anime.setTitolo(titolo);
        anime.setCoverUrl(coverUrl);
        anime.setDescrizione(descrizione);
        anime.setAnnoUscita(annoUscita);
        anime.setStudio(studio);
        anime.setGenere(genere);
        anime.setEpisodi(episodi);
        anime.setInCorso(inCorso != null ? inCorso : false);

        animeRepository.save(anime);

        return "redirect:/";
    }
}
