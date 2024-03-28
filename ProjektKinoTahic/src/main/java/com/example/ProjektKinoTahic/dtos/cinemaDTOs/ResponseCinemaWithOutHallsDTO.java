package com.example.ProjektKinoTahic.dtos.cinemaDTOs;

import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCinemaWithOutHallsDTO {
    private int cinemaId;

    private String name;

    private String address;

    private String manager;

    private int maxHalls;

}
