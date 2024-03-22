package com.example.ProjektKinoTahic.entities;

import com.example.ProjektKinoTahic.enums.MovieVersion;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Film")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String mainCharacter;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String premieredAt;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieVersion movieVersion;
    @ManyToOne
    @JoinColumn(name = "hallId")
    private Hall hall;
}
