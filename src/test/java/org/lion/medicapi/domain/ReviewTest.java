package org.lion.medicapi.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ReviewTest {

    @Test
    @DisplayName("94년생의 생년월일 6자를 입력하면, 나이대가 30대로 계산되어 나온다.")
    void calculateAgeGroup() {

        // given
        final String birthDt = "940828";
        final int birthYear = Integer.parseInt(birthDt.substring(0, 2));
        System.out.println("birthYear = " + birthYear);

        // when
        final int ageGroup = calculateAgeGroup(birthYear);

        // then
        Assertions.assertThat(ageGroup).isEqualTo(30);
    }

    private int calculateAgeGroup(final int birthYear) {
        final int year = LocalDateTime.now().getYear() - 2000;
        System.out.println("year = " + year);

        int age;
        if (birthYear > year) {
            age = 100 - birthYear + year + 1;
        } else {
            age = year - birthYear + 1;
        }
        System.out.println("age = " + age);

        return (age / 10) * 10;
    }
}