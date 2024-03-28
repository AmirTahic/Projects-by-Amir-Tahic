package com.example.ProjektKinoTahic.dtos.hallDTOs;

import com.example.ProjektKinoTahic.enums.MovieVersion;
import lombok.*;

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

}
