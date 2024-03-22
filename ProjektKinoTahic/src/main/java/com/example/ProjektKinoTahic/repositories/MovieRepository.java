package com.example.ProjektKinoTahic.repositories;

import com.example.ProjektKinoTahic.entities.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Integer> {
}
