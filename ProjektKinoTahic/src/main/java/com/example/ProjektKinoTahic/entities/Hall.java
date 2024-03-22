package com.example.ProjektKinoTahic.entities;

import com.example.ProjektKinoTahic.enums.MovieVersion;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Saal")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hallId;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int occupiedSeats;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieVersion supportedMovieVersion;

    @ManyToOne
    @JoinColumn(name = "cinemaId", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    private List<Movie> movieList;
}
