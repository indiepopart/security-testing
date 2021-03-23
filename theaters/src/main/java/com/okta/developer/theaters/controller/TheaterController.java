package com.okta.developer.theaters.controller;

import com.okta.developer.theaters.repository.TheaterRepository;
import com.okta.developer.theaters.model.Theater;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TheaterController {

    private TheaterRepository theaterRepository;

    public TheaterController(TheaterRepository theaterRepository){
        this.theaterRepository = theaterRepository;
    }

    @GetMapping("/theater")
    public Flux<Theater> getAllTheaters(){
        return theaterRepository.findAll();
    }

    @PostMapping("/theater")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('THEATER_create')")
    public Mono<Void> saveTheater(@RequestBody Theater theater){
        theaterRepository.save(theater);
        return Mono.empty();
    }

}
