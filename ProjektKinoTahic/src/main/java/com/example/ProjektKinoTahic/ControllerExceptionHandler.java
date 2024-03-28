package com.example.ProjektKinoTahic;

import com.example.ProjektKinoTahic.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ProblemDetail kinoNotFound(CinemaNotFoundException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Kino nicht gefunden");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail kinoDeleted(CinemaDeletedException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.OK);
        problem.setTitle("Kino gelöscht");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail kinoNotDeletable(CinemaNotDeletableException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle("Kino nicht gelöscht");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail maxHallsReached(MaxHallsReachedException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle("Maximale Anzahl erreicht!");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail hallNotFound(HallNotFoundException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Halle nicht gefunden");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail movieNotFound(MovieNotFoundException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Film nicht gefunden");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail invalidMovieFormat(InvalidMovieFormatException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle("Falsche Filmversion");
        problem.setDetail(e.getMessage());
        return problem;
    }
    @ExceptionHandler
    public ProblemDetail hallNotChangeable(HallNotChangeableException e){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle("Halle kann nicht bearbeitet werden!");
        problem.setDetail(e.getMessage());
        return problem;
    }

}
