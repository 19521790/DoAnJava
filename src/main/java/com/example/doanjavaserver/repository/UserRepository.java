package com.example.doanjavaserver.repository;

import com.example.doanjavaserver.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByNameAndPassword(String name, String password);

    User findByNameAndEmail(String name, String email);

    boolean existsByNameOrEmail(String name, String email);
}
