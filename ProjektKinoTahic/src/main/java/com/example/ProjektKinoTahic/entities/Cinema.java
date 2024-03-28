package com.example.ProjektKinoTahic.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cinemaId;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 30, nullable = false)
    private String manager;

    @Column(nullable = false)
    private int maxHalls;

    @OneToMany(mappedBy = "cinema")
    private List<Hall> hallList;
}