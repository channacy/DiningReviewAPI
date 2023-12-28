package com.example.diningReviewAPI.entities;
import com.example.diningReviewAPI.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name="DINING_REVIEW")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiningReview{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;

    @Column(name="REVIEWER")
    private String reviewer;

    @Column(name="RESTAURANT_ID")
    private long restaurantID;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="PEANUT_REVIEW")
    private Float peanutReview;

    @Column(name="EGG_REVIEW")
    private Float eggReview;

    @Column(name="DAIRY_REVIEW")
    private Float dairyReview;

    @Column(name="COMMENTARY")
    private String commentary;
}

