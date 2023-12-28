package com.example.diningReviewAPI.repository;
import com.example.diningReviewAPI.entities.DiningReview;
import com.example.diningReviewAPI.entities.Restaurant;
import com.example.diningReviewAPI.enums.Status;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long>{
    DiningReview findByID(Long ID);
    List<DiningReview> findByStatus(Status status);
    List<DiningReview> findByStatusAndRestaurantID(Status status, Long id);

}
