# 🍽️ Dining Review API

This API facilitates the submission of dining reviews for restaurants by registered users. The platform allows users and restaurants to register, and reviews are assigned numerical values, with particular emphasis on accommodating preferences related to food allergies such as eggs, dairy, and peanuts.


## Tech Stack


**Server:** Java, Spring Boot Framework, JPA (Java Persistence API), Spring Data JPA, Curl

**Client:** NA


## Run Locally

Clone the project

```bash
  git clone https://github.com/channacy/DiningReviewAPI
```

Go to the project directory

```bash
  cd diningReviewAPI
```

Install dependencies (using Maven)
```bash
   mvn clean install
```

Start the server

```bash
   mvn spring-boot:run
```


## Running Tests

To run tests, run the following command

```bash
  curl -X POST -H "Content-Type: application/json" -d '{
  "username": "john_doe",
  "city": "New York",
  "state": "NY",
  "zipcode": "10001",
  "isInterestedPeanutAllergy": true,
  "isInterestedEggAllergy": false,
  "isInterestedDiaryAllergy": true
}' http://localhost:8080/user

```


```bash
  curl -X PUT -H "Content-Type: application/json" -d '{
  "city": "New York",
  "state": "NY",
  "zipcode": "900",
  "isInterestedPeanutAllergy": true,
  "isInterestedEggAllergy": false,
  "isInterestedDiaryAllergy": true
}' http://localhost:8080/user/john_doe

```


```bash
  curl -X POST \
  http://localhost:8080/restaurants \
  -H 'Content-Type: application/json' \
  -d '{
    "name": "Sample Restaurant",
    "zipcode": "12345",
    "peanutReview": 4.5,
    "eggReview": 3.0,
    "dairyReview": 2.5,
    "overallScore": 3.3,
    "numReviews": 10
  }'
```


```bash
  curl -X POST \
  http://localhost:8080/reviews \
  -H 'Content-Type: application/json' \
  -d '{
    "reviewer": "John Doe",
    "restaurantID": 1,
    "status": "PENDING",
    "peanutReview": 4.5,
    "eggReview": 3.0,
    "dairyReview": 2.5,
    "commentary": "Great experience!"
  }'

```

```bash
  curl -X GET \
  http://localhost:8080/admin/reviews/pending

```


```bash
  curl -X GET \
  http://localhost:8080/reviews/approved/123
```


```bash
  curl -X GET \
  http://localhost:8080/restaurants/find/1
```


```bash
   curl -X PUT \
  -H "Content-Type: application/json" \
  -d '{
    "isAccepted": true
  }' \
  http://localhost:8080/admin/1/1/validate
```



```bash
  curl -X GET http://localhost:8080/restaurants/12345

```
## Acknowledgements

 - [Codecademy - Create REST APIs with Spring and Java](https://www.codecademy.com/enrolled/paths/create-rest-apis-with-spring-and-java)

