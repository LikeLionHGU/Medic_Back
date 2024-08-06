package org.lion.medicapi;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.dto.request.LikeRequestV2;
import org.lion.medicapi.dto.request.LoginRequestV2;
import org.lion.medicapi.dto.request.ReviewRequestV2;
import org.lion.medicapi.dto.request.SignUpRequestV2;
import org.lion.medicapi.dto.response.*;
import org.lion.medicapi.entity.LikeV2;
import org.lion.medicapi.entity.ProductV2;
import org.lion.medicapi.entity.ReviewV2;
import org.lion.medicapi.entity.UserV2;
import org.lion.medicapi.exception.ApiException;
import org.lion.medicapi.exception.ErrorType;
import org.lion.medicapi.repository.LikeRepositoryV2;
import org.lion.medicapi.repository.ProductRepositoryV2;
import org.lion.medicapi.repository.ReviewRepositoryV2;
import org.lion.medicapi.repository.UserRepositoryV2;
import org.lion.medicapi.type.TagType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiService {

    private final UserRepositoryV2 userRepositoryV2;
    private final ProductRepositoryV2 productRepositoryV2;
    private final ReviewRepositoryV2 reviewRepositoryV2;
    private final LikeRepositoryV2 likeRepositoryV2;

    // 로그인한 사용자의 닉네임 반환
    public String getNickname(Long userId) {
        UserV2 user = userRepositoryV2.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND_SPECIFIC));
        return user.getName();
    }

    // 로그인한 사용자의 태그 리스트 반환
    public List<TagType> getUserTags(Long userId) {
        UserV2 user = userRepositoryV2.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND_SPECIFIC));
        return user.getTagTypes();
    }

    // 회원가입
    @Transactional
    public void signUp(SignUpRequestV2 signUpRequest) {
        // 이메일 중복 체크
        if (userRepositoryV2.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiException(ErrorType.DUPLICATE_EMAIL);
        }

        // 닉네임 중복 체크
        if (userRepositoryV2.existsByName(signUpRequest.getName())) {
            throw new ApiException(ErrorType.DUPLICATE_NAME);
        }

        // SupplementType 리스트 크기 확인
        if (signUpRequest.getSupplementTypes().size() > 3) {
            throw new ApiException(ErrorType.TOO_MANY_SUPPLEMENT_TYPES);
        }

        // 유저 생성
        UserV2 user = new UserV2(
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                signUpRequest.getPassword(), // 비밀번호는 암호화해서 저장해야 좋지만 현재는 그냥 저장
                signUpRequest.getBirthDate(),
                signUpRequest.getGenderType(),
                signUpRequest.getTagTypes(),
                signUpRequest.getSupplementTypes()
        );

        userRepositoryV2.save(user);
    }

    // 로그인
    public LoginResponseV2 login(LoginRequestV2 loginRequest, HttpSession session) {
        // 이메일 존재 여부 확인
        UserV2 user = userRepositoryV2.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ApiException(ErrorType.EMAIL_NOT_FOUND));

        // 비밀번호 검증
        if (!loginRequest.getPassword().equals(user.getPassword())) {
            throw new ApiException(ErrorType.PASSWORD_INCORRECT);
        }

        // 세션에 사용자 정보 저장
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userTags", user.getTagTypes());

        // 응답 객체 생성 및 반환
        LoginResponseV2 response = new LoginResponseV2();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setTags(user.getTagTypes());

        return response;
    }

    // 태그 변경
    @Transactional
    public List<ProductSimpleResponseV2> updateTags(Long userId, List<String> tags) {
        UserV2 user = userRepositoryV2.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND_SPECIFIC));

        List<TagType> tagTypes = tags.stream()
                .map(TagType::valueOf)
                .distinct()
                .collect(Collectors.toList());

        if (tagTypes.size() > 3) {
            throw new ApiException(ErrorType.MAX_TAG_EXCEEDED);
        }

        user.setTagTypes(tagTypes);
        userRepositoryV2.save(user);

        // 각 태그에 대해 제품 목록을 랜덤으로 섞어서 추출
        List<ProductSimpleResponseV2> selectedProducts = tagTypes.stream()
                .flatMap(tag -> {
                    List<ProductV2> productsByTag = productRepositoryV2.findByTagTypes(List.of(tag));
                    Collections.shuffle(productsByTag);
                    return productsByTag.stream()
                            .map(product -> {
                                Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                                return new ProductSimpleResponseV2(
                                        product.getId(),
                                        product.getImageUrl(),
                                        product.getName(),
                                        product.getNormalPrice(),
                                        product.getSalePrice(),
                                        reviewCount.intValue(),
                                        product.getTagType()
                                );
                            });
                })
                .collect(Collectors.toList());

        // 부족한 제품 수를 채우기 위해 추가로 섞기
        Collections.shuffle(selectedProducts);
        List<ProductSimpleResponseV2> finalProducts = selectedProducts.stream()
                .limit(6)
                .collect(Collectors.toList());

        // 만약 6개가 안된다면 추가로 채우기
        if (finalProducts.size() < 6) {
            List<ProductV2> extraProducts = productRepositoryV2.findByTagTypes(tagTypes);
            Collections.shuffle(extraProducts);
            finalProducts.addAll(extraProducts.stream()
                    .map(product -> {
                        Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                        return new ProductSimpleResponseV2(
                                product.getId(),
                                product.getImageUrl(),
                                product.getName(),
                                product.getNormalPrice(),
                                product.getSalePrice(),
                                reviewCount.intValue(),
                                product.getTagType()
                        );
                    })
                    .filter(p -> !finalProducts.contains(p))
                    .limit(6 - finalProducts.size())
                    .collect(Collectors.toList()));
        }

        // 최종적으로 6개 제품 반환
        Collections.shuffle(finalProducts);
        return finalProducts.stream().limit(6).collect(Collectors.toList());
    }

    // 모든 상품 목록 조회
    public List<ProductSimpleResponseV2> listAllProducts(List<TagType> tags, String sort) {
        List<ProductV2> products;

        if (tags == null || tags.isEmpty()) {
            products = productRepositoryV2.findAll();
        } else {
            products = productRepositoryV2.findByTagTypes(tags);
        }

        // 정렬 로직 추가
        switch (sort.toLowerCase()) {
            case "review":
                products.sort((p1, p2) -> Long.compare(reviewRepositoryV2.countByProductId(p2.getId()), reviewRepositoryV2.countByProductId(p1.getId())));
                break;
            case "highprice":
                products.sort((p1, p2) -> Integer.compare(p2.getSalePrice(), p1.getSalePrice()));
                break;
            case "lowprice":
                products.sort((p1, p2) -> Integer.compare(p1.getSalePrice(), p2.getSalePrice()));
                break;
            default:
                products.sort(Comparator.comparing(ProductV2::getId, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
        }

        return products.stream()
                .map(product -> {
                    Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                    return new ProductSimpleResponseV2(
                            product.getId(),
                            product.getImageUrl(),
                            product.getName(),
                            product.getNormalPrice(),
                            product.getSalePrice(),
                            reviewCount.intValue(),
                            product.getTagType()
                    );
                })
                .collect(Collectors.toList());
    }

    // 맞춤 상품 목록 조회
    public List<ProductSimpleResponseV2> listCustomProducts(List<TagType> tags) {
        // 각 태그에 대해 제품 목록을 랜덤으로 섞어서 추출
        List<ProductSimpleResponseV2> selectedProducts = tags.stream()
                .flatMap(tag -> {
                    List<ProductV2> productsByTag = productRepositoryV2.findByTagTypes(List.of(tag));
                    Collections.shuffle(productsByTag);
                    return productsByTag.stream()
                            .map(product -> {
                                Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                                return new ProductSimpleResponseV2(
                                        product.getId(),
                                        product.getImageUrl(),
                                        product.getName(),
                                        product.getNormalPrice(),
                                        product.getSalePrice(),
                                        reviewCount.intValue(),
                                        product.getTagType()
                                );
                            });
                })
                .collect(Collectors.toList());

        // 부족한 제품 수를 채우기 위해 추가로 섞기
        Collections.shuffle(selectedProducts);
        List<ProductSimpleResponseV2> finalProducts = selectedProducts.stream()
                .limit(6)
                .collect(Collectors.toList());

        // 만약 6개가 안된다면 추가로 채우기
        if (finalProducts.size() < 6) {
            List<ProductV2> extraProducts = productRepositoryV2.findByTagTypes(tags);
            Collections.shuffle(extraProducts);
            finalProducts.addAll(extraProducts.stream()
                    .map(product -> {
                        Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                        return new ProductSimpleResponseV2(
                                product.getId(),
                                product.getImageUrl(),
                                product.getName(),
                                product.getNormalPrice(),
                                product.getSalePrice(),
                                reviewCount.intValue(),
                                product.getTagType()
                        );
                    })
                    .filter(p -> !finalProducts.contains(p))
                    .limit(6 - finalProducts.size())
                    .collect(Collectors.toList()));
        }

        // 최종적으로 6개 제품 반환
        Collections.shuffle(finalProducts);
        return finalProducts.stream().limit(6).collect(Collectors.toList());
    }

    // BEST 상품 목록 조회
    public List<ProductSimpleResponseV2> listBestProducts() {
        List<ProductV2> products = productRepositoryV2.findAll();

        // 모든 상품 목록을 무작위로 섞기
        Collections.shuffle(products);

        // 상위 3개의 상품을 선택하여 반환
        return products.stream()
                .limit(3)
                .map(product -> {
                    Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                    return new ProductSimpleResponseV2(
                            product.getId(),
                            product.getImageUrl(),
                            product.getName(),
                            product.getNormalPrice(),
                            product.getSalePrice(),
                            reviewCount.intValue(),
                            product.getTagType()
                    );
                })
                .collect(Collectors.toList());
    }

    // 토글에 표시되는 추천 상품 목록 조회 (유저 네임 포함)
    public ProductRecommendResponseV2 listRecommendProducts(Long userId) {
        UserV2 user = userRepositoryV2.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND_SPECIFIC));

        List<ProductSimpleResponseV2> recommendedProducts = user.getSupplementTypes().stream()
                .map(supplementType -> {
                    String recommendedProduct = supplementType.getRecommendedProduct();
                    return recommendedProduct;
                })
                .distinct()
                .map(productName -> {
                    return productRepositoryV2.findByName(productName)
                            .map(product -> {
                                Long reviewCount = reviewRepositoryV2.countByProductId(product.getId());
                                return new ProductSimpleResponseV2(
                                        product.getId(),
                                        product.getImageUrl(),
                                        product.getName(),
                                        product.getNormalPrice(),
                                        product.getSalePrice(),
                                        reviewCount.intValue(),
                                        product.getTagType()
                                );
                            })
                            .orElseGet(() -> {
                                return null;
                            });
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new ProductRecommendResponseV2(user.getName(), recommendedProducts);
    }

    // 상품 상세 조회
    public ProductDetailResponseV2 getProductDetail(Long productId) {
        ProductV2 product = productRepositoryV2.findById(productId)
                .orElseThrow(() -> new ApiException(ErrorType.PRODUCT_NOT_FOUND));

        return new ProductDetailResponseV2(
                product.getId(),
                product.getImageUrl(),
                product.getName(),
                product.getManufacturer(),
                product.getNormalPrice(),
                product.getSalePrice(),
                product.getReportNumber(),
                product.getRegisterDate(),
                product.getExpirationDate(),
                product.getIntakeMethod(),
                product.getIngestPrecaution(),
                product.getFunctionallyContents(),
                product.getOtherMaterials(),
                product.getTagType()
        );
    }

    public ReviewListResponseV2 getProductReviews(Long productId, String sort) {
        List<ReviewV2> reviews = reviewRepositoryV2.findByProductId(productId);

        // 정렬 로직 추가
        switch (sort.toLowerCase()) {
            case "recommend":
                reviews.sort((r1, r2) -> Long.compare(likeRepositoryV2.countByReviewId(r2.getId()), likeRepositoryV2.countByReviewId(r1.getId())));
                break;
            case "age_10s":
            case "age_20s":
            case "age_30s":
            case "age_40s":
            case "age_50s":
            case "age_60s":
            case "age_70s":
                String ageRange = sort.split("_")[1].substring(0, 2) + "대";
                reviews = reviews.stream()
                        .filter(review -> calculateAgeRange(review.getUser().getBirthDate()).equals(ageRange))
                        .collect(Collectors.toList());
                break;
            case "all":
            case "latest":
            default:
                reviews.sort((r1, r2) -> r2.getReviewDate().compareTo(r1.getReviewDate()));
                break;
        }

        List<ReviewResponseV2> reviewResponses = reviews.stream()
                .map(review -> {
                    Long likeCount = likeRepositoryV2.countByReviewId(review.getId());
                    String ageRange = calculateAgeRange(review.getUser().getBirthDate());
                    return new ReviewResponseV2(
                            review.getId(),
                            review.getUser().getName(),
                            review.getProduct().getName(),
                            review.getUser().getGenderType(),
                            ageRange,
                            review.getStar(),
                            review.getContents(),
                            review.getPurchaseDate(),
                            review.getReviewDate(),
                            likeCount.intValue()
                    );
                })
                .collect(Collectors.toList());

        double averageRating = reviews.stream().mapToInt(ReviewV2::getStar).average().orElse(0.0);
        int reviewCount = reviews.size();

        return new ReviewListResponseV2(reviewCount, averageRating, reviewResponses);
    }

    private String calculateAgeRange(String birthDate) {
        LocalDate birth;
        try {
            if (birthDate.length() == 6) {
                int yearPrefix = (birthDate.charAt(0) >= '0' && birthDate.charAt(0) <= '3') ? 2000 : 1900;
                int year = Integer.parseInt(birthDate.substring(0, 2));
                int month = Integer.parseInt(birthDate.substring(2, 4));
                int day = Integer.parseInt(birthDate.substring(4, 6));
                birth = LocalDate.of(yearPrefix + year, month, day);
            } else if (birthDate.length() == 8) {
                birth = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            } else {
                throw new ApiException(ErrorType.INVALID_BIRTHDATE_FORMAT);
            }
        } catch (DateTimeParseException e) {
            throw new ApiException(ErrorType.INVALID_BIRTHDATE_FORMAT);
        }

        int currentYear = LocalDate.now().getYear();
        int birthYear = birth.getYear();
        int age = currentYear - birthYear;

        if (birth.plusYears(age).isAfter(LocalDate.now())) {
            age--;
        }

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

    // 리뷰 추가
    @Transactional
    public void addReview(ReviewRequestV2 reviewRequest) {
        UserV2 user = userRepositoryV2.findById(reviewRequest.getUserId())
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND_SPECIFIC));
        ProductV2 product = productRepositoryV2.findById(reviewRequest.getProductId())
                .orElseThrow(() -> new ApiException(ErrorType.PRODUCT_NOT_FOUND));

        ReviewV2 review = new ReviewV2(
                user,
                product,
                reviewRequest.getStar(),
                reviewRequest.getContents(),
                reviewRequest.getPurchaseDate(),
                reviewRequest.getReviewDate()
        );

        reviewRepositoryV2.save(review);
    }

    // 좋아요 추가
    @Transactional
    public void addLike(LikeRequestV2 likeRequest) {
        UserV2 user = userRepositoryV2.findById(likeRequest.getUserId())
                .orElseThrow(() -> new ApiException(ErrorType.USER_NOT_FOUND_SPECIFIC));
        ReviewV2 review = reviewRepositoryV2.findById(likeRequest.getReviewId())
                .orElseThrow(() -> new ApiException(ErrorType.REVIEW_NOT_FOUND));

        LikeV2 like = new LikeV2(user, review);
        likeRepositoryV2.save(like);
    }
}
