package com.example.ProjektKinoTahic.exceptions;

public class CinemaNotDeletableException extends RuntimeException{

    public CinemaNotDeletableException(){super("Kino kann nicht gelöscht werden da ein Film in einem Saal läuft!");}
}
