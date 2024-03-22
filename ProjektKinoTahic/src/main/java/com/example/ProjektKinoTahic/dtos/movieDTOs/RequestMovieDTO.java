package com.example.ProjektKinoTahic.dtos.movieDTOs;

import com.example.ProjektKinoTahic.enums.MovieVersion;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMovieDTO {

    private String title;

    private String mainCharacter;

    private String description;

    private String premieredAt;

    private MovieVersion movieVersion;

    private int hallId;
}
