package com.example.ProjektKinoTahic.dtos.cinemaDTOs;

import com.example.ProjektKinoTahic.dtos.hallDTOs.ResponseHallDTO;
import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCinemaDTO {

    private int cinemaId;

    private String name;

    private String address;

    private String manager;

    private int maxHalls;

    private List<ResponseHallDTO> hallList;
}
