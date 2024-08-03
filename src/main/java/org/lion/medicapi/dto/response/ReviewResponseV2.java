package org.lion.medicapi.dto.response;

import lombok.Data;
import org.lion.medicapi.type.GenderType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
public class ReviewResponseV2 {

    private Long id;
    private String userName;
    private String productName;
    private GenderType gender;
    private String ageRange;
    private Integer star;
    private String contents;
    private String purchaseDate;
    private String reviewDate;
    private Integer likeCnt;

    public ReviewResponseV2(Long id, String userName, String productName, GenderType gender, String birthDate, Integer star, String contents, String purchaseDate, String reviewDate, Integer likeCnt) {
        this.id = id;
        this.userName = userName;
        this.productName = productName;
        this.gender = gender;
        this.ageRange = calculateAgeRange(birthDate);
        this.star = star;
        this.contents = contents;
        this.purchaseDate = purchaseDate;
        this.reviewDate = reviewDate;
        this.likeCnt = likeCnt;
    }

    private String calculateAgeRange(String birthDate) {
        LocalDate birth;
        try {
            if (birthDate.length() == 6) {
                birth = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyMMdd"));
            } else if (birthDate.length() == 8) {
                birth = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            } else {
                throw new IllegalArgumentException("Invalid birthDate format");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid birthDate format", e);
        }

        int currentYear = LocalDate.now().getYear();
        int birthYear = birth.getYear();
        int age = currentYear - birthYear;

        if (age < 10) {
            return "10대 미만";
        } else if (age < 20) {
            return "10대";
        } else if (age < 30) {
            return "20대";
        } else if (age < 40) {
            return "30대";
        } else if (age < 50) {
            return "40대";
        } else if (age < 60) {
            return "50대";
        } else if (age < 70) {
            return "60대";
        } else {
            return "70대 이상";
        }
    }
}