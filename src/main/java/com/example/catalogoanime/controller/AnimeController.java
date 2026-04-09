package com.example.catalogoanime.controller;

import com.example.catalogoanime.model.Anime;
import com.example.catalogoanime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
