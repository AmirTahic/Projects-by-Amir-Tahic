package com.example.ProjektKinoTahic.services;

import com.example.ProjektKinoTahic.dtos.cinemaDTOs.RequestCinemaDTO;
import com.example.ProjektKinoTahic.dtos.cinemaDTOs.ResponseCinemaDTO;
import com.example.ProjektKinoTahic.dtos.hallDTOs.ResponseHallDTO;
import com.example.ProjektKinoTahic.entities.Cinema;
import com.example.ProjektKinoTahic.entities.Hall;
import com.example.ProjektKinoTahic.repositories.CinemaRepository;
import com.example.ProjektKinoTahic.repositories.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        }catch (DataIntegrityViolationException e){
            //Only when something in RequestBody is missing
            return new ResponseEntity<>("Bitte fülle alle Felder aus",HttpStatus.BAD_REQUEST);
        }
    }

    //Searches Cinema by ID in Database and returns it as DTO
    public ResponseEntity<?> getCinemaById(int cinemaId) {

        //Gets Cinema by ID from Repository
        Cinema cinema = cinemaRepository.findCinemaByCinemaId(cinemaId);

        //Checks if cinema is null
        if (cinema == null) {
            //Returns not found
            return new ResponseEntity<>("Das Kino existiert nicht!",HttpStatus.NOT_FOUND);
        }
        //Returns Cinema as DTO
        return new ResponseEntity<>(convertCinemaWithHallsToDTO(cinema), HttpStatus.OK);

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
    public ResponseEntity<String> deleteCinemaById(int cinemaId) {
        Cinema cinema = cinemaRepository.findCinemaByCinemaId(cinemaId);

        try{
        //Checks if cinema exists by ID
        if (cinemaRepository.existsById(cinemaId)) {
            //Deletes all Halls in the Cinema first
            for (Hall hall : cinema.getHallList()) {
                //Deletes the Halls only when there is no Movie in the Hall
                if (hall.getMovieList().isEmpty())
                    hallRepository.deleteById(hall.getHallId());
            }
            //Deletes existing cinema
            cinemaRepository.deleteById(cinemaId);

            return new ResponseEntity<>("Kino gelöscht!", HttpStatus.OK);
        }
        }catch(DataIntegrityViolationException e) {
            //When cinema not existing returns not found
            return new ResponseEntity<>("Kino kann nicht gelöscht werden da ein Film in einem Saal läuft!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Das Kino wurde nicht gefunden!", HttpStatus.NOT_FOUND);
    }

    private Cinema createCinema(RequestCinemaDTO requestCinemaDTO) {
        //returns the created cinema. Cinema got created from request body
        return new Cinema().builder()
                .name(requestCinemaDTO.getName())
                .address(requestCinemaDTO.getAddress())
                .manager(requestCinemaDTO.getManager())
                .maxHalls(requestCinemaDTO.getMaxHalls())
                .hallList(null)
                .build();
    }

    private ResponseCinemaDTO convertCinemaToDTO(Cinema cinema) {
        //Returns the cinema as DTO
        return new ResponseCinemaDTO().builder()
                .cinemaId(cinema.getCinemaId())
                .name(cinema.getName())
                .address(cinema.getAddress())
                .manager(cinema.getManager())
                .maxHalls(cinema.getMaxHalls())
                .build();
    }

    private ResponseCinemaDTO convertCinemaWithHallsToDTO(Cinema cinema) {
        //Converts the created cinema to DTO with Halls
        return new ResponseCinemaDTO().builder()
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
            ResponseHallDTO responseHallDTO = new ResponseHallDTO().builder()
                    .hallId(hall.getHallId())
                    .capacity(hall.getCapacity())
                    .occupiedSeats(hall.getOccupiedSeats())
                    .supportedMovieVersion(hall.getSupportedMovieVersion())
                    .cinemaId(cinema.getCinemaId())
                    .build();

            //Saves the hallDTO in the created list above
            responseHallDTOList.add(responseHallDTO);
        }
        //Returns the created list with hallDTO´s
        return responseHallDTOList;
    }
}
