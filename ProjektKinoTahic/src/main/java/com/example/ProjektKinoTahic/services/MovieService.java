package com.example.ProjektKinoTahic.services;

import com.example.ProjektKinoTahic.dtos.movieDTOs.RequestMovieDTO;
import com.example.ProjektKinoTahic.dtos.movieDTOs.ResponseMovieDTO;
import com.example.ProjektKinoTahic.entities.Hall;
import com.example.ProjektKinoTahic.entities.Movie;
import com.example.ProjektKinoTahic.exceptions.HallNotFoundException;
import com.example.ProjektKinoTahic.exceptions.InvalidMovieFormatException;
import com.example.ProjektKinoTahic.exceptions.MovieNotFoundException;
import com.example.ProjektKinoTahic.repositories.HallRepository;
import com.example.ProjektKinoTahic.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final HallRepository hallRepository;
    private final MovieRepository movieRepository;

    public ResponseEntity<?> createMovieAndAddToDb(RequestMovieDTO requestMovieDTO){
        Optional<Hall> optionalHall = hallRepository.findById(requestMovieDTO.getHallId());
        if (optionalHall.isPresent()) {

            Hall hall = optionalHall.get();
            Movie movie = createMovie(requestMovieDTO, hall);

            if (hall.getSupportedMovieVersion() == movie.getMovieVersion()) {
                movieRepository.save(movie);
                return new ResponseEntity<>(convertMovieToDTO(movie), HttpStatus.OK);
            }
            throw new InvalidMovieFormatException();
        }
        throw new HallNotFoundException();
    }

    public ResponseEntity<?> editMovie(int movieId, RequestMovieDTO requestMovieDTO){
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        Optional<Hall> optionalHall = hallRepository.findById(requestMovieDTO.getHallId());

        if (optionalHall.isPresent()){

           Hall hall = optionalHall.get();

            if(optionalMovie.isPresent()) {

                Movie movie = optionalMovie.get();
                setChanges(requestMovieDTO,hall,movie);

                if (hall.getSupportedMovieVersion() == movie.getMovieVersion()) {
                    movieRepository.save(movie);
                    return new ResponseEntity<>(convertMovieToDTO(movie), HttpStatus.OK);
                }
                throw new InvalidMovieFormatException();
            }
            throw new MovieNotFoundException();
        }
        throw new HallNotFoundException();
    }


    private Movie createMovie(RequestMovieDTO requestMovieDTO, Hall hall){
        return Movie.builder()
                .title(requestMovieDTO.getTitle())
                .mainCharacter(requestMovieDTO.getMainCharacter())
                .description(requestMovieDTO.getDescription())
                .premieredAt(requestMovieDTO.getPremieredAt())
                .movieVersion(requestMovieDTO.getMovieVersion())
                .hall(hall)
                .build();
    }

    private ResponseMovieDTO convertMovieToDTO(Movie movie){
        return ResponseMovieDTO.builder()
                .movieId(movie.getMovieId())
                .title(movie.getTitle())
                .mainCharacter(movie.getMainCharacter())
                .description(movie.getDescription())
                .premieredAt(movie.getPremieredAt())
                .movieVersion(movie.getMovieVersion())
                .hallId(movie.getHall().getHallId())
                .build();
    }

    private void setChanges(RequestMovieDTO requestMovieDTO, Hall hall, Movie movie){
        movie.setTitle(requestMovieDTO.getTitle());
        movie.setMainCharacter(requestMovieDTO.getMainCharacter());
        movie.setDescription(requestMovieDTO.getDescription());
        movie.setPremieredAt(requestMovieDTO.getPremieredAt());
        movie.setMovieVersion(requestMovieDTO.getMovieVersion());
        movie.setHall(hall);
    }
}
