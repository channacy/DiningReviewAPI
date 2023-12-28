package com.example.diningReviewAPI.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RESTAURANT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;

    @Column(name="NAME")
    private String name;

    @Column(name="ZIPCODE")
    private String zipcode;

    @Column(name="PEANUT_REVIEW")
    private Float peanutReview;

    @Column(name="EGG_REVIEW")
    private Float eggReview;

    @Column(name="DAIRY_REVIEW")
    private Float dairyReview;

    @Column(name="OVERALL_SCORE")
    private Float overallScore;

    @Column(name="NUM_REVIEWS")
    private int numReviews;
}


