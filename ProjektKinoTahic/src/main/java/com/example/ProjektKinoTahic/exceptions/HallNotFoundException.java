package com.example.ProjektKinoTahic.exceptions;

public class HallNotFoundException extends RuntimeException{
    public HallNotFoundException(){
        super("Die gewünschte Halle existiert nicht!");
    }
}
