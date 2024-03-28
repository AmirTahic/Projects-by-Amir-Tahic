package com.example.ProjektKinoTahic.exceptions;

public class HallNotChangeableException extends RuntimeException{
    public HallNotChangeableException(){
        super("Die Halle kann nicht bearbeitet werden da ein Film in der Halle läuft!");
    }
}
