package org.lion.medicapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lion.medicapi.type.GenderType;
import org.lion.medicapi.type.SupplementType;
import org.lion.medicapi.type.TagType;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_v2")
public class UserV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String name; // 닉네임

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String birthDate; // 생년월일 (yyyyMMdd)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType genderType; // 성별

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_tags", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "tag")
    private List<TagType> tagTypes; // 태그

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_supplements", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "supplement")
    private List<SupplementType> supplementTypes; // 보충제 유형

    public UserV2(String email, String name, String password, String birthDate, GenderType genderType, List<TagType> tagTypes, List<SupplementType> supplementTypes) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.genderType = genderType;
        this.tagTypes = tagTypes;
        this.supplementTypes = supplementTypes;
    }

    public void setTagTypes(List<TagType> tagTypes) {
        this.tagTypes = tagTypes;
    }
}