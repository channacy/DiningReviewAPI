package com.example.diningReviewAPI.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.diningReviewAPI.entities.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String>{
    User findByID(Long ID);
    User findByUsername(String username);
}

