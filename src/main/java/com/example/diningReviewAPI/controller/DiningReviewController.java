package com.example.diningReviewAPI.controller;
import com.example.diningReviewAPI.entities.DiningReview;
import com.example.diningReviewAPI.entities.Restaurant;
import com.example.diningReviewAPI.enums.Status;
import com.example.diningReviewAPI.exceptions.QueryNotSupportedException;
import com.example.diningReviewAPI.model.AdminReviewAction;
import com.example.diningReviewAPI.repository.DiningReviewRepository;
import com.example.diningReviewAPI.repository.RestaurantRepository;
import com.example.diningReviewAPI.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.diningReviewAPI.entities.User;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DiningReviewController {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DiningReviewRepository diningReviewRepository;

    public DiningReviewController(UserRepository userRepository, RestaurantRepository restaurantRepository, DiningReviewRepository diningReviewRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.diningReviewRepository = diningReviewRepository;
    }
    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        User newUser = this.userRepository.save(user);
        return newUser;
    }

    @PutMapping("/user/{username}")
    public User updateUser(
         @PathVariable("username") String username,
         @RequestBody User user
        ){
        Optional<User> userToUpdateOptional = Optional.ofNullable(this.userRepository.findByUsername(username));
        if(!userToUpdateOptional.isPresent()){
            return null;
        }
        User userToUpdate = userToUpdateOptional.get();
        if(user.getCity() != null){
            userToUpdate.setCity(user.getCity());
        }
        if(user.getState() != null){
            userToUpdate.setState(user.getState());
        }
        if(user.getZipcode() != null){
            userToUpdate.setZipcode(user.getZipcode());
        }
        if(user.getIsInterestedDiaryAllergy() != null){
            userToUpdate.setIsInterestedPeanutAllergy(user.getIsInterestedDiaryAllergy());
        }
        if(user.getIsInterestedEggAllergy() != null){
            userToUpdate.setIsInterestedEggAllergy(user.getIsInterestedEggAllergy());
        }
        if(user.getIsInterestedPeanutAllergy() != null){
            userToUpdate.setIsInterestedPeanutAllergy(user.getIsInterestedPeanutAllergy());
        }
        User updatedUser = this.userRepository.save(userToUpdate);
        return updatedUser;
    }

    @GetMapping("/users/search")
    public User searchUsers(@RequestParam(required = true) String username) throws QueryNotSupportedException {
        if (username != null) {
            return userRepository.findByUsername(username);
        }
        else{
            throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
        }
    }

    private void validateUser(String username){
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByUsername(username));
        if(existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/reviews")
    public DiningReview createDiningReview(@RequestBody DiningReview diningReview){
        validateUser(diningReview.getReviewer());
        DiningReview newDiningReview = this.diningReviewRepository.save(diningReview);
            return newDiningReview;
    }

    @GetMapping("/admin/reviews/pending")
    public List<DiningReview> getPendingReviews(){
        return diningReviewRepository.findByStatus(Status.valueOf("PENDING"));
    }

    @GetMapping("/reviews/approved/{id}")
    public List<DiningReview> getApprovedReviewsByRestaurantID(@PathVariable("id") Long id){
        return diningReviewRepository.findByStatusAndRestaurantID(Status.valueOf("APPROVED"), id);
    }
    @PutMapping("admin/{restaurantID}/{reviewID}/validate")
    public DiningReview validateDiningReview(@PathVariable("restaurantID") Long restaurantID, @PathVariable("reviewID") Long reviewID, @RequestBody AdminReviewAction adminReviewAction){
        Optional<DiningReview> diningReviewToValidateOptional = Optional.ofNullable(this.diningReviewRepository.findByID(reviewID));
        Optional<Restaurant> restaurantToValidateOptional = Optional.ofNullable(this.restaurantRepository.findByID(restaurantID));
        if(!diningReviewToValidateOptional.isPresent() || !restaurantToValidateOptional.isPresent()){
            return null;
        }

        DiningReview diningReviewToUpdate = diningReviewToValidateOptional.get();
        Restaurant restaurantToUpdate = restaurantToValidateOptional.get();

        if(adminReviewAction.isAccepted()){
            diningReviewToUpdate.setStatus(Status.valueOf("ACCEPTED"));
            if(diningReviewToUpdate.getDairyReview() != null &&  restaurantToUpdate.getDairyReview() != null){
                Float newDiaryReview;
                if(restaurantToUpdate.getNumReviews() == 0){
                    newDiaryReview = diningReviewToUpdate.getDairyReview();
                }
                else{
                    newDiaryReview = (diningReviewToUpdate.getDairyReview() + restaurantToUpdate.getDairyReview()) / 2;
                }
                int newNumReviews = restaurantToUpdate.getNumReviews() + 1;
                restaurantToUpdate.setDairyReview(newDiaryReview);
                restaurantToUpdate.setNumReviews(newNumReviews);
            }

            if(diningReviewToUpdate.getPeanutReview() != null &&  restaurantToUpdate.getPeanutReview() != null){
                Float newPeanutReview;
                if(restaurantToUpdate.getNumReviews() == 0) {
                    newPeanutReview = diningReviewToUpdate.getDairyReview();
                }
                else{
                    newPeanutReview = (diningReviewToUpdate.getDairyReview() + restaurantToUpdate.getDairyReview()) / 2;
                }
                int newNumReviews = restaurantToUpdate.getNumReviews() + 1;
                restaurantToUpdate.setPeanutReview(newPeanutReview);
                restaurantToUpdate.setNumReviews(newNumReviews);
            }

            if(diningReviewToUpdate.getEggReview() != null &&  restaurantToUpdate.getEggReview() != null){
                Float newEggReview;
                if(restaurantToUpdate.getNumReviews() == 0){
                    newEggReview = diningReviewToUpdate.getEggReview();
                }
                else{
                    newEggReview = (diningReviewToUpdate.getEggReview() + restaurantToUpdate.getEggReview()) / 2;
                }
                int newNumReviews = restaurantToUpdate.getNumReviews() + 1;
                restaurantToUpdate.setPeanutReview(newEggReview);
                restaurantToUpdate.setNumReviews(newNumReviews);
            }

            if(restaurantToUpdate.getEggReview() != null && restaurantToUpdate.getPeanutReview() != null && restaurantToUpdate.getDairyReview() != null){
                Float newOverallReview = (restaurantToUpdate.getEggReview() + restaurantToUpdate.getDairyReview() + restaurantToUpdate.getPeanutReview()) / 3;
                restaurantToUpdate.setOverallScore(newOverallReview);
            }
        }

        else{
            diningReviewToUpdate.setStatus(Status.valueOf("REJECTED"));
        }

        DiningReview updatedDiningReview = this.diningReviewRepository.save(diningReviewToUpdate);
        return updatedDiningReview;
    }

    @PostMapping("/restaurants")
    public Restaurant submitRestaurantEntry(@RequestBody Restaurant restaurant){
        Optional<Restaurant> existingRestaurant = this.restaurantRepository.findByNameAndZipcode(restaurant.getName(), restaurant.getZipcode());
        if(existingRestaurant.isPresent()){
            return null;
        }
        else{
            Restaurant newRestaurant = this.restaurantRepository.save(restaurant);
            return newRestaurant;
        }
    }

    @GetMapping("/restaurants/find/{id}")
    public Optional<Restaurant> getRestaurantByID(@PathVariable("id") Long id){
        return this.restaurantRepository.findById(id);
    }

    @GetMapping("/restaurants/{zipcode}")
    public List<Restaurant> getRestaurantByZipcodeAndAllergyInfo(@PathVariable("zipcode") String zipcode){
        List<Restaurant> possibleRestaurants = restaurantRepository.findByZipcode(zipcode);
        List<Restaurant> restaurantsWithReviews = new ArrayList<>();
        if(possibleRestaurants.size() == 0){
            return null;
        }
        else{
            for (Restaurant restaurant : possibleRestaurants) {
                if(restaurant.getNumReviews() > 0){
                    restaurantsWithReviews.add(restaurant);
                }
            }
            List<Restaurant> sortedRestaurants = restaurantsWithReviews.stream()
                    .sorted(Comparator.comparingInt(Restaurant::getNumReviews).reversed())
                    .collect(Collectors.toList());

            return sortedRestaurants;

        }

    }






}


