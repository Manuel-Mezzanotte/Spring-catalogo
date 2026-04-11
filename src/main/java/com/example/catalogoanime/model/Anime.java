package com.example.catalogoanime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String coverUrl;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    private Integer annoUscita;

    private String studio;

    private String genere;

    private Integer episodi;
    
    private Boolean inCorso = false;
}
