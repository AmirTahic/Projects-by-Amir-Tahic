package com.example.ProjektKinoTahic.services;

import com.example.ProjektKinoTahic.dtos.hallDTOs.RequestHallDTO;
import com.example.ProjektKinoTahic.dtos.hallDTOs.ResponseHallDTO;
import com.example.ProjektKinoTahic.entities.Cinema;
import com.example.ProjektKinoTahic.entities.Hall;
import com.example.ProjektKinoTahic.enums.MovieVersion;
import com.example.ProjektKinoTahic.repositories.CinemaRepository;
import com.example.ProjektKinoTahic.repositories.HallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
                return new ResponseEntity<>("Die maximale Anzahl an Sääle wurde erreicht", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("Das gewünschte Kino existiert nicht", HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<?> showEditedHall(int hallId, RequestHallDTO requestHallDTO) {

        Optional<Hall> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isPresent()) {
            Hall hall = optionalHall.get();
            editHallAndSave(hall, requestHallDTO);

            return new ResponseEntity<>(convertHallToDTO(hall), HttpStatus.OK);
        }

        return new ResponseEntity<>("Gewünschte Halle existiert nicht!", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> showHall(int hallId) {
        Optional<Hall> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isPresent()) {
            Hall hall = optionalHall.get();
            return new ResponseEntity<>(convertHallToDTO(hall), HttpStatus.OK);
        }
        return new ResponseEntity<>("Gewünschte Halle existiert nicht!", HttpStatus.NOT_FOUND);
    }

    private ResponseHallDTO convertHallToDTO(Hall hall) {
        return new ResponseHallDTO().builder()
                .hallId(hall.getHallId())
                .capacity(hall.getCapacity())
                .occupiedSeats(hall.getOccupiedSeats())
                .supportedMovieVersion(hall.getSupportedMovieVersion())
                .cinemaId(hall.getCinema().getCinemaId())
                .build();
    }

    private Hall createHall(RequestHallDTO requestHallDTO, Cinema cinema) {

        return new Hall().builder()
                .capacity(requestHallDTO.getCapacity())
                .occupiedSeats(requestHallDTO.getOccupiedSeats())
                .supportedMovieVersion(requestHallDTO.getSupportedMovieVersion())
                .cinema(cinema)
                .build();
    }

    private void editHallAndSave(Hall hall, RequestHallDTO requestHallDTO) {
        if (hall.getMovieList().isEmpty()) {
            hall.setCapacity(requestHallDTO.getCapacity());
            hall.setOccupiedSeats(requestHallDTO.getOccupiedSeats());
            if (MovieVersion.DBOX == hall.getSupportedMovieVersion() && requestHallDTO.getSupportedMovieVersion() == MovieVersion.R3D) {
                hall.setSupportedMovieVersion(requestHallDTO.getSupportedMovieVersion());
            }
            hallRepository.save(hall);
        }
    }
}
