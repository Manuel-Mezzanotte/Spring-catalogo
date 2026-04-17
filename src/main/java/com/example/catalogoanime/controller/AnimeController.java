package com.example.catalogoanime.controller;

import com.example.catalogoanime.model.Anime;
import com.example.catalogoanime.model.AppUser;
import com.example.catalogoanime.repository.AnimeRepository;
import com.example.catalogoanime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "titolo,asc") String sort,
            Model model, 
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortObj = Sort.by(sortDirection, sortField);

        List<Anime> animes;
        if (search != null && !search.trim().isEmpty()) {
            animes = animeRepository.findByUserAndTitoloContainingIgnoreCase(currentUser, search, sortObj);
            model.addAttribute("search", search);
        } else {
            animes = animeRepository.findByUser(currentUser, sortObj);
        }
        
        model.addAttribute("sortParam", sort);
        model.addAttribute("animes", animes);
        return "home";
    }

    @GetMapping("/new")
    public String newAnime() {
        return "new";
    }

    @GetMapping("/item/{id}")
    public String itemDetail(@org.springframework.web.bind.annotation.PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Anime anime = animeRepository.findById(id).orElse(null);
        
        if (anime == null || !anime.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/";
        }
        
        model.addAttribute("anime", anime);
        return "item";
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
            @RequestParam(required = false) Boolean inCorso,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Anime anime = new Anime();
        anime.setTitolo(titolo);
        anime.setCoverUrl(coverUrl);
        anime.setDescrizione(descrizione);
        anime.setAnnoUscita(annoUscita);
        anime.setStudio(studio);
        anime.setGenere(genere);
        anime.setEpisodi(episodi);
        anime.setInCorso(inCorso != null ? inCorso : false);
        anime.setUser(currentUser);

        anime = animeRepository.save(anime);
        
        redirectAttributes.addFlashAttribute("successMessage", "Anime inserito con successo!");
        return "redirect:/item/" + anime.getId();
    }

    @PostMapping("/api/anime/delete/{id}")
    public String deleteAnime(@org.springframework.web.bind.annotation.PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Anime anime = animeRepository.findById(id).orElse(null);
        
        if (anime != null && anime.getUser().getId().equals(currentUser.getId())) {
            animeRepository.delete(anime);
            redirectAttributes.addFlashAttribute("successMessage", "Anime eliminato dal catalogo.");
        }
        
        return "redirect:/";
    }

    @PostMapping("/api/anime/delete-all")
    public String deleteAllAnime(@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Anime> animes = animeRepository.findByUserOrderByTitoloAsc(currentUser);
        animeRepository.deleteAll(animes);
        redirectAttributes.addFlashAttribute("successMessage", "Catalogo svuotato completamente.");
        return "redirect:/";
    }
}
