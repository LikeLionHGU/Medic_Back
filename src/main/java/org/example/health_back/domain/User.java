package org.example.health_back.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.health_back.dto.UserDto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 기본 키를 데이터 베이스에 위임한다.
    private Long userId;
    private String userNickname;
    private String userEmail;
    private String userPassword;
    private int age;        // 그에 따른 값을 계산해서 넣어준다.
    private String birthDigit;
    private boolean gender;

    @ElementCollection
    @CollectionTable(name = "user_drugs", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "drug")
    private List<String> drugInUse;

    @ElementCollection
    @CollectionTable(name = "health_hash_tag", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "hash_tag")
    private List<String> healthHashTag;

    public static User toUser(UserDto userDto) {
        User user = User.builder()
                .userNickname(userDto.getUserNickname())
                .userPassword(userDto.getUserPassword())
                .userEmail(userDto.getUserEmail())
                .birthDigit(userDto.getBirthDigit())
                .gender(userDto.isGender())
                .drugInUse(userDto.getDrugInUse())
                .healthHashTag(userDto.getHealthHashTag())
                .build();
        user.calculateAge();  // 나이 계산 로직 호출
        return user;
    }

    // 나이 계산 로직
    public void calculateAge() {
        String birthYear = this.birthDigit.substring(0, 2);
        String birthMonth = this.birthDigit.substring(2, 4);
        String birthDay = this.birthDigit.substring(4, 6);

        int year = Integer.parseInt(birthYear);
        if (year < 100) {
            if (year <= LocalDate.now().getYear() % 100) {
                year += 2000;  // 2000년대 출생자
            } else {
                year += 1900;  // 1900년대 출생자
            }
        }

        LocalDate birthDate = LocalDate.of(year, Integer.parseInt(birthMonth), Integer.parseInt(birthDay));
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
    }
}

