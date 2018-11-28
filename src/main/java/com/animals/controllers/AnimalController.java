package com.animals.controllers;
import com.animals.models.Animal;
import com.animals.models.Users;
import com.animals.JSONUtils;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;

import com.animals.requests.AnimalRequest;
import com.animals.services.AnimalService;
import com.animals.repositories.AnimalsRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController
@RequestMapping(path = "/animal", produces = "application/json")
public class AnimalController {
    private final AnimalsRepository repository;

    private final AnimalService animalService;

    public AnimalController(
            final AnimalsRepository repository,
            final AnimalService animalService
    ) {
        this.repository = repository;
        this.animalService = animalService;
    }

    @GetMapping(headers = {"Authorization"})
    public List<Animal> getAllAnimals() {
        return animalService.getAnimals();
    }

    @GetMapping(path = "{id}", headers = {"Authorization"})
    public Animal getAnimal(@PathVariable String id) {
        return animalService.getAnimalById(id);
    }

    @PostMapping(headers = {"Authorization"})
    public ResponseEntity<Animal> createAnimals(
            @RequestBody AnimalRequest request
    ){
        return new ResponseEntity<>(animalService.createAnimal(request), HttpStatus.CREATED);
    }

    @PatchMapping(path = "{id}", headers = {"Authorization"})
    public Animal updateAnimal(@PathVariable String id, @RequestBody AnimalRequest request) {
        return animalService.editAnimal(id, request);
    }

    @DeleteMapping(path = "{id}", headers = {"Authorization"})
    public ResponseEntity deleteAnimal(@PathVariable String id) {
        animalService.deleteAnimalById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}