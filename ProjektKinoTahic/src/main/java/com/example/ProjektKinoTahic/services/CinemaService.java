package com.example.ProjektKinoTahic.services;

import com.example.ProjektKinoTahic.dtos.cinemaDTOs.RequestCinemaDTO;
import com.example.ProjektKinoTahic.dtos.cinemaDTOs.ResponseCinemaDTO;
import com.example.ProjektKinoTahic.dtos.cinemaDTOs.ResponseCinemaWithOutHallsDTO;
import com.example.ProjektKinoTahic.dtos.hallDTOs.ResponseHallDTO;
import com.example.ProjektKinoTahic.entities.Cinema;
import com.example.ProjektKinoTahic.entities.Hall;
import com.example.ProjektKinoTahic.exceptions.CinemaDeletedException;
import com.example.ProjektKinoTahic.exceptions.CinemaNotDeletableException;
import com.example.ProjektKinoTahic.exceptions.CinemaNotFoundException;
import com.example.ProjektKinoTahic.repositories.CinemaRepository;
import com.example.ProjektKinoTahic.repositories.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;

    //Creates Cinema and adds it to Database and returns DTO to Frontend
    public ResponseEntity<?> addCinemaToDb(RequestCinemaDTO requestCinemaDTO) {
        try {
            //Creates Cinema
            Cinema cinema = createCinema(requestCinemaDTO);
            //Adds Cinema to Database
            cinemaRepository.save(cinema);
            //Returns the DTO to Frontend
            return new ResponseEntity<>(convertCinemaToDTO(cinema), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            //Only when something in RequestBody is missing
            return new ResponseEntity<>("Bitte fülle alle Felder aus", HttpStatus.BAD_REQUEST);
        }
    }

    //Searches Cinema by ID in Database and returns it as DTO
    public ResponseEntity<?> getCinemaById(int cinemaId) {

        //Gets Cinema by ID from Repository
        Optional<Cinema> optionalCinema = cinemaRepository.findById(cinemaId);

        //Checks if cinema exists
        if (optionalCinema.isPresent()) {
            Cinema cinema = optionalCinema.get();
            //Returns Cinema as DTO
            return new ResponseEntity<>(convertCinemaWithHallsToDTO(cinema), HttpStatus.OK);
        }
        //Returns not found
        throw new CinemaNotFoundException();
    }

    //Gets all Cinemas from Repository and saves in a cinemaDTO List and returns the DTO List
    public List<ResponseCinemaDTO> getAllCinemasFromDb() {

        //List for cinemaDTOs
        List<ResponseCinemaDTO> cinemaDTOList = new ArrayList<>();
        //For each cinema in Repository creates DTO and puts in DTO list
        for (Cinema cinema : cinemaRepository.findAll()) {
            //Adds converted cinema to DTO list
            cinemaDTOList.add(convertCinemaWithHallsToDTO(cinema));
        }
        return cinemaDTOList;

    }

    //Searches the cinema by ID and deletes it
    public ResponseEntity<?> deleteCinemaById(int cinemaId) {
        Optional<Cinema> optionalCinema = cinemaRepository.findById(cinemaId);

        if (optionalCinema.isPresent()) {
            Cinema cinema = optionalCinema.get();
            //Checks if there is a movie in a hall
            for (Hall hall : cinema.getHallList()) {
                if (!hall.getMovieList().isEmpty()) {
                    throw new CinemaNotDeletableException();
                }
            }
            //If there is no movie in a hall it deletes all halls first
            for (Hall hall : cinema.getHallList()) {
                hallRepository.deleteById(hall.getHallId());
            }
            //Then deletes the cinema
            cinemaRepository.deleteById(cinemaId);
            throw new CinemaDeletedException();
        }
        throw new CinemaNotFoundException();
    }

    private Cinema createCinema(RequestCinemaDTO requestCinemaDTO) {
        //returns the created cinema. Cinema got created from request body
        return Cinema.builder()
                .name(requestCinemaDTO.getName())
                .address(requestCinemaDTO.getAddress())
                .manager(requestCinemaDTO.getManager())
                .maxHalls(requestCinemaDTO.getMaxHalls())
                .hallList(null)
                .build();
    }

    private ResponseCinemaWithOutHallsDTO convertCinemaToDTO(Cinema cinema) {
        //Returns the cinema as DTO
        return ResponseCinemaWithOutHallsDTO.builder()
                .cinemaId(cinema.getCinemaId())
                .name(cinema.getName())
                .address(cinema.getAddress())
                .manager(cinema.getManager())
                .maxHalls(cinema.getMaxHalls())
                .build();
    }

    private ResponseCinemaDTO convertCinemaWithHallsToDTO(Cinema cinema) {
        //Converts the created cinema to DTO with Halls
        return ResponseCinemaDTO.builder()
                .cinemaId(cinema.getCinemaId())
                .name(cinema.getName())
                .address(cinema.getAddress())
                .manager(cinema.getManager())
                .maxHalls(cinema.getMaxHalls())
                .hallList(convertHallListFromCinemaToDTO(cinema))
                .build();
    }

    private List<ResponseHallDTO> convertHallListFromCinemaToDTO(Cinema cinema) {
        //Creates List for HallDTO´s
        List<ResponseHallDTO> responseHallDTOList = new ArrayList<>();

        //For every Hall in the cinema hall get converted into a DTO
        for (Hall hall : cinema.getHallList()) {
            ResponseHallDTO responseHallDTO = ResponseHallDTO.builder()
                    .hallId(hall.getHallId())
                    .capacity(hall.getCapacity())
                    .occupiedSeats(hall.getOccupiedSeats())
                    .supportedMovieVersion(hall.getSupportedMovieVersion())
                    .build();

            //Saves the hallDTO in the created list above
            responseHallDTOList.add(responseHallDTO);
        }
        //Returns the created list with hallDTO´s
        return responseHallDTOList;
    }
}
