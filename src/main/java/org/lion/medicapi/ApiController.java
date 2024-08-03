package org.lion.medicapi;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.dto.request.*;
import org.lion.medicapi.dto.response.*;
import org.lion.medicapi.type.TagType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestV2 signUpRequest) {
        apiService.signUp(signUpRequest);
        return ResponseEntity.ok("success");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseV2> login(@RequestBody LoginRequestV2 loginRequest, HttpSession session) {
        LoginResponseV2 response = apiService.login(loginRequest, session);
        return ResponseEntity.ok(response);
    }

    // 로그아웃 (로그인 필요)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("success");
    }

    // 태그 추가 (로그인 필요)
    @PostMapping("/users/tags")
    public ResponseEntity<List<TagType>> addTag(@RequestBody TagRequestV2 tagRequest, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        TagType tag = TagType.valueOf(tagRequest.getTag());
        List<TagType> updatedTags = apiService.addTag(userId, tag);
        return ResponseEntity.ok(updatedTags);
    }

    // 태그 제거 (로그인 필요)
    @DeleteMapping("/users/tags")
    public ResponseEntity<List<TagType>> removeTag(@RequestBody TagRequestV2 tagRequest, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        TagType tag = TagType.valueOf(tagRequest.getTag());
        List<TagType> updatedTags = apiService.removeTag(userId, tag);
        return ResponseEntity.ok(updatedTags);
    }

    // 모든 상품 목록 조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductSimpleResponseV2>> listAllProducts(
            @RequestParam(required = false) List<TagType> tags,
            @RequestParam(required = false, defaultValue = "latest") String sort) {
        List<ProductSimpleResponseV2> products = apiService.listAllProducts(tags, sort);
        return ResponseEntity.ok(products);
    }

    // 맞춤 상품 목록 조회 (로그인 필요)
    @GetMapping("/products/custom")
    public ResponseEntity<List<ProductSimpleResponseV2>> listCustomProducts(
            HttpSession session,
            @RequestParam(required = false, defaultValue = "latest") String sort) {
        @SuppressWarnings("unchecked")
        List<TagType> userTags = (List<TagType>) session.getAttribute("userTags");

        if (userTags == null || userTags.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<ProductSimpleResponseV2> customizedProducts = apiService.listCustomProducts(userTags, sort);
        return ResponseEntity.ok(customizedProducts);
    }

    // BEST 상품 목록 조회
    @GetMapping("/products/best")
    public ResponseEntity<List<ProductSimpleResponseV2>> listBestProducts() {
        List<ProductSimpleResponseV2> bestProducts = apiService.listBestProducts();
        return ResponseEntity.ok(bestProducts);
    }

    // 추천 상품 목록 조회 (로그인 필요)
    @GetMapping("/products/recommend")
    public ResponseEntity<ProductRecommendResponseV2> listRecommendProducts(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        ProductRecommendResponseV2 response = apiService.listRecommendProducts(userId);
        return ResponseEntity.ok(response);
    }

    // 상품 상세 조회
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailResponseV2> getProductDetail(@PathVariable Long productId) {
        ProductDetailResponseV2 productDetail = apiService.getProductDetail(productId);
        return ResponseEntity.ok(productDetail);
    }

    // 상품 상세에 대한 리뷰 리스트 조회
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewListResponseV2> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(required = false, defaultValue = "latest") String sort) {
        ReviewListResponseV2 reviewList = apiService.getProductReviews(productId, sort);
        return ResponseEntity.ok(reviewList);
    }

    // 리뷰 추가 (로그인 필요)
    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@RequestBody ReviewRequestV2 review, HttpSession session) {
        apiService.addReview(review);
        return ResponseEntity.ok("success");
    }

    // 좋아요 추가 (로그인 필요)
    @PostMapping("/likes")
    public ResponseEntity<String> addLike(@RequestBody LikeRequestV2 like, HttpSession session) {
        apiService.addLike(like);
        return ResponseEntity.ok("success");
    }
}
