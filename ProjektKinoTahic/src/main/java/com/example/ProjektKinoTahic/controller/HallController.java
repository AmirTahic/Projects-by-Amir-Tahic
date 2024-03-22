package com.example.ProjektKinoTahic.controller;

import com.example.ProjektKinoTahic.dtos.hallDTOs.RequestHallDTO;
import com.example.ProjektKinoTahic.dtos.hallDTOs.ResponseHallDTO;
import com.example.ProjektKinoTahic.services.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/hall")
public class HallController {

    private final HallService hallService;
    @PostMapping
    public ResponseEntity<?> createHallAndAddToDb(@RequestBody RequestHallDTO requestHallDTO){
    return hallService.createHallAndAddToDb(requestHallDTO);
    }

    @PutMapping ("/{hallId}")
    public  ResponseEntity<?> editHall(@PathVariable int hallId, @RequestBody RequestHallDTO requestHallDTO){
        return hallService.showEditedHall(hallId,requestHallDTO);
    }

    @GetMapping("/{hallId}")
    public ResponseEntity<?> showHall(@PathVariable int hallId){
        return hallService.showHall(hallId);
    }
}
