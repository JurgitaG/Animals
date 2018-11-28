package com.animals.repositories;

        import com.animals.models.Users;
        import org.springframework.data.mongodb.repository.MongoRepository;
        import org.springframework.stereotype.Repository;
        import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository extends MongoRepository<Users, String> {
    Users findByUsername(String username);
    Long countByUsername(String email);



}