package com.server.repository;

import com.server.model.User;
import com.server.repository.template.UserTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String>, UserTemplate {
    User findByNameAndPassword(String name, String password);

    User findByNameAndEmail(String name, String email);

    boolean existsByNameOrEmail(String name, String email);

    User findByEmail(String email);
}
