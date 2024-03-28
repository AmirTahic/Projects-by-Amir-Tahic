package com.example.ProjektKinoTahic.exceptions;

public class MaxHallsReachedException extends RuntimeException{
    public MaxHallsReachedException(){
        super("Die maximale Anzahl an Sääle wurde erreicht");
    }
}
