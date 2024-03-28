package com.example.ProjektKinoTahic.services;

import com.example.ProjektKinoTahic.dtos.hallDTOs.RequestHallDTO;
import com.example.ProjektKinoTahic.dtos.hallDTOs.ResponseHallWithCinemaIdDTO;
import com.example.ProjektKinoTahic.entities.Cinema;
import com.example.ProjektKinoTahic.entities.Hall;
import com.example.ProjektKinoTahic.enums.MovieVersion;
import com.example.ProjektKinoTahic.exceptions.HallNotChangeableException;
import com.example.ProjektKinoTahic.exceptions.HallNotFoundException;
import com.example.ProjektKinoTahic.exceptions.CinemaNotFoundException;
import com.example.ProjektKinoTahic.exceptions.MaxHallsReachedException;
import com.example.ProjektKinoTahic.repositories.CinemaRepository;
import com.example.ProjektKinoTahic.repositories.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HallService {

    private final HallRepository hallRepository;
    private final CinemaRepository cinemaRepository;

    public ResponseEntity<?> createHallAndAddToDb(RequestHallDTO requestHallDTO) {

        Optional<Cinema> optionalCinema = cinemaRepository.findById(requestHallDTO.getCinemaId());

        if (optionalCinema.isPresent()) {
            Cinema cinema = optionalCinema.get();

            if (cinema.getHallList().size() < cinema.getMaxHalls()) {
                Hall hall = createHall(requestHallDTO, cinema);

                hallRepository.save(hall);

                return new ResponseEntity<>(convertHallToDTO(hall), HttpStatus.OK);
            } else {
                throw new MaxHallsReachedException();
            }
        }
       throw new CinemaNotFoundException();

    }

    public ResponseEntity<?> showEditedHall(int hallId, RequestHallDTO requestHallDTO) {

        Optional<Hall> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isPresent()) {
            Hall hall = optionalHall.get();

            hallRepository.save(editHall(hall,requestHallDTO));

            return new ResponseEntity<>(convertHallToDTO(hall), HttpStatus.OK);
        }

        throw new HallNotFoundException();
    }

    public ResponseEntity<?> showHall(int hallId) {
        Optional<Hall> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isPresent()) {
            Hall hall = optionalHall.get();
            return new ResponseEntity<>(convertHallToDTO(hall), HttpStatus.OK);
        }
        throw new HallNotFoundException();
    }

    private ResponseHallWithCinemaIdDTO convertHallToDTO(Hall hall) {
        return ResponseHallWithCinemaIdDTO.builder()
                .hallId(hall.getHallId())
                .capacity(hall.getCapacity())
                .occupiedSeats(hall.getOccupiedSeats())
                .supportedMovieVersion(hall.getSupportedMovieVersion())
                .cinemaId(hall.getCinema().getCinemaId())
                .build();
    }

    private Hall createHall(RequestHallDTO requestHallDTO, Cinema cinema) {

        return Hall.builder()
                .capacity(requestHallDTO.getCapacity())
                .occupiedSeats(requestHallDTO.getOccupiedSeats())
                .supportedMovieVersion(requestHallDTO.getSupportedMovieVersion())
                .cinema(cinema)
                .build();
    }

    private Hall editHall(Hall getHall, RequestHallDTO requestHallDTO) {
        Hall hall = getHall;
        if (hall.getMovieList().isEmpty()) {
            hall.setCapacity(requestHallDTO.getCapacity());
            hall.setOccupiedSeats(requestHallDTO.getOccupiedSeats());
            if (MovieVersion.DBOX == hall.getSupportedMovieVersion() && requestHallDTO.getSupportedMovieVersion() == MovieVersion.R3D) {
                hall.setSupportedMovieVersion(requestHallDTO.getSupportedMovieVersion());
            }
            return hall;
        }
        throw new HallNotChangeableException();
    }
}