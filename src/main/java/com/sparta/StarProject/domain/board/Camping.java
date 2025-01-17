package com.sparta.StarProject.domain.board;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.User;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("Camping")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Camping extends Board{

    private String campingData;

    public Camping(String locationName, String content, String img, User user, String campingData) {
        super(locationName, content, img, user);
        this.campingData = campingData;
    }
}
