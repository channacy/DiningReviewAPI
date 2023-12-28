package com.example.diningReviewAPI.repository;

import com.example.diningReviewAPI.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> findByNameAndZipcode(String name, String zipcode);
    Restaurant findByID(Long ID);
    List<Restaurant> findByZipcodeAndOverallScore(String zipcode, Long overallScore);
    List<Restaurant> findByZipcode(String zipcode);

}
