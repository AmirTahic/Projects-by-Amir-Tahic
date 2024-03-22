package com.example.ProjektKinoTahic.dtos.hallDTOs;

import com.example.ProjektKinoTahic.entities.Cinema;
import com.example.ProjektKinoTahic.entities.Movie;
import com.example.ProjektKinoTahic.enums.MovieVersion;
import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHallDTO {
    private int hallId;

    private int capacity;

    private int occupiedSeats;

    private MovieVersion supportedMovieVersion;

    private int cinemaId;

//    private List<Movie> movieList;
}
