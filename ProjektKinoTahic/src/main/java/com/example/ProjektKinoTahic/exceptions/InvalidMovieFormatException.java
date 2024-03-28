package com.example.ProjektKinoTahic.exceptions;

public class InvalidMovieFormatException extends RuntimeException{
    public InvalidMovieFormatException(){
        super("Der Saal unterstützt die Filmversion nicht!");
    }
}
