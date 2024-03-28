package com.example.ProjektKinoTahic.exceptions;

public class CinemaNotFoundException extends RuntimeException{

    public CinemaNotFoundException(){
        super("Das gewünschte Kino existiert nicht!");
    }
}
