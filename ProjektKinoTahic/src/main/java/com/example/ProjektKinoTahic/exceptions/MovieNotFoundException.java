package com.example.ProjektKinoTahic.exceptions;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){
        super("Der gewünschte Film existiert nicht!");
    }
}
