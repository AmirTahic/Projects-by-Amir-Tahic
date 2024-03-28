package com.example.ProjektKinoTahic.exceptions;

public class CinemaDeletedException extends RuntimeException{

    public CinemaDeletedException(){
        super("Das gewünschte Kino wurde gelöscht");
    }
}
