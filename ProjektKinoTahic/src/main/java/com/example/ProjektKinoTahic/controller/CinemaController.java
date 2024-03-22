package com.example.ProjektKinoTahic.controller;

import com.example.ProjektKinoTahic.dtos.cinemaDTOs.RequestCinemaDTO;
import com.example.ProjektKinoTahic.dtos.cinemaDTOs.ResponseCinemaDTO;
import com.example.ProjektKinoTahic.services.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cinema")
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<?> addCinemaToDb(@RequestBody RequestCinemaDTO requestCinemaDTO){

     return cinemaService.addCinemaToDb(requestCinemaDTO);
    }

    @GetMapping("/{cinemaId}")
    public ResponseEntity<?> getCinemaById (@PathVariable int cinemaId){
        return cinemaService.getCinemaById(cinemaId);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCinemaDTO>> getAllCinemasFromDb(){

        return new ResponseEntity<>(cinemaService.getAllCinemasFromDb(), HttpStatus.OK) ;
    }

    @DeleteMapping("/{cinemaId}")
    public ResponseEntity<?> deleteCinemaById(@PathVariable int cinemaId){

        return new ResponseEntity<>(cinemaService.deleteCinemaById(cinemaId), cinemaService.deleteCinemaById(cinemaId).getStatusCode());
    }

}
