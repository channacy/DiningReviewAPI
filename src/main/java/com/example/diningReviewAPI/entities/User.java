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
@Table(name="APP_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long ID;

    @Column(name="USERNAME", unique = true)
    private String username;

    @Column(name="CITY")
    private String city;

    @Column(name="STATE")
    private String state;

    @Column(name="ZIPCODE")
    private String zipcode;

    @Column(name="PEANUT_ALLERGY_INTEREST")
    private Boolean isInterestedPeanutAllergy;

    @Column(name="EGG_ALLERGY_INTEREST")
    private Boolean isInterestedEggAllergy;

    @Column(name="DIARY_ALLERGY_INTEREST")
    private Boolean isInterestedDiaryAllergy;
}


