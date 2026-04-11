    @GetMapping("/item/{id}")
    public String itemDetail(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        AppUser currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Anime anime = animeRepository.findById(id).orElse(null);
        
        if (anime == null || !anime.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/";
        }
        
        model.addAttribute("anime", anime);
        return "item";
    }
