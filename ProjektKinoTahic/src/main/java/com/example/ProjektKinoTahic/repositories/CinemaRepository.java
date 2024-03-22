package com.example.ProjektKinoTahic.repositories;

import com.example.ProjektKinoTahic.entities.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Integer> {
    public Cinema findCinemaByCinemaId(int cinemaId);
}
