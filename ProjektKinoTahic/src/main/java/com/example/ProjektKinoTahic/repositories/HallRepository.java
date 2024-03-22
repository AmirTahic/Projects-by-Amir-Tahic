package com.example.ProjektKinoTahic.repositories;

import com.example.ProjektKinoTahic.entities.Hall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends CrudRepository<Hall,Integer> {
}
