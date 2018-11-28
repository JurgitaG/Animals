package com.animals.services;

import com.animals.models.Users;
import com.animals.models.Animal;
import com.animals.repositories.UsersRepository;
import com.animals.repositories.AnimalsRepository;
import com.animals.exceptions.AnimalNotFoundException;
import com.animals.requests.AnimalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static com.animals.config.SecurityConstants.EXPIRATION_TIME;
import static com.animals.config.SecurityConstants.KEY;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalsRepository repository;
    private final UsersRepository usersRepository;

    @Autowired
    public AnimalService(final AnimalsRepository repository, final UsersRepository usersRepository) {
        this.repository = repository;
        this.usersRepository = usersRepository;
    }

    public Animal createAnimal(AnimalRequest request){
        validateRequest(request);
        Animal animal = new Animal(
                request.getName(),
                request.getNum(),
                request.getBirthDate()
        );
        Users user = (Users)SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        String userId = user.get_id();
        animal.setOwnerId(userId);
        return repository.save(animal);
    }
    public Animal getAnimalById(String id) {
        Optional<Animal> animal = repository.findById(id);
        if (animal.isPresent()) {
            return animal.get();
        }
        throw new AnimalNotFoundException("Animal not found!");
    }
    public List<Animal> getAnimals() {
            return repository.findAll();
    }
    public Animal editAnimal(final String id, final AnimalRequest request) {
        Optional<Animal> book = repository.findById(id);
        if (book.isPresent()) {
            Animal animal = book.get();
            validateRequest(request);
            animal.setNum(request.getNum());
            animal.setName(request.getName());
            animal.setOwnerId(request.getOwnerId());
            animal.setBirthDate(request.getBirthDate());
            return repository.save(animal);
        }
        throw new AnimalNotFoundException("Animal not found!");
    }
    public void deleteAnimalById(final String id) {
        if (!repository.existsById(id)) throw new AnimalNotFoundException("Animal is not found!");
        repository.deleteById(id);
    }


    private void validateRequest(AnimalRequest request){
        if (request.getName() == null || request.getName().length() == 0
                || request.getBirthDate() == null
                || request.getNum() == null || request.getNum().length() == 0
        ) {
            throw new UsernameNotFoundException("Privalote užpildyti visus laukus!");
        }
    }
}
