package com.example.ProjektKinoTahic.dtos.cinemaDTOs;

import com.example.ProjektKinoTahic.entities.Hall;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCinemaDTO {

    private int cinemaId;

    private String name;

    private String address;

    private String manager;

    private int maxHalls;

}
