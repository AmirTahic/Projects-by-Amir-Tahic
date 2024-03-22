package com.example.ProjektKinoTahic.controller;

import com.example.ProjektKinoTahic.dtos.movieDTOs.RequestMovieDTO;
import com.example.ProjektKinoTahic.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/movie")
public class MovieController {

    private final MovieService movieService;
    @PostMapping
    public ResponseEntity<?> createMovieAndAddToDb(@RequestBody RequestMovieDTO requestMovieDTO){
        return movieService.createMovieAndAddToDb(requestMovieDTO);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> editMovie(@PathVariable int movieId, @RequestBody RequestMovieDTO requestMovieDTO){
        return movieService.editMovie(movieId, requestMovieDTO);
    }
}
