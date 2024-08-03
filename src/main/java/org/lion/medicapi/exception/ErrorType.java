package org.lion.medicapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    UNEXPECTED_ERROR("E000", "예기치 않은 오류가 발생하였습니다."),

    SESSION_NOT_FOUND("E001", "세션을 찾을 수 없습니다."),
    MISSING_HEADER("E002", "요청 헤더가 누락되었습니다."),
    INVALID_REQUEST_PARAMETER("E003", "잘못된 요청 파라미터입니다."),
    MISSING_REQUEST_PARAMETER("E004", "요청 파라미터가 누락되었습니다."),
    INVALID_REQUEST_BODY("E005", "잘못된 요청 본문입니다."),
    PERMISSION_DENIED("E006", "접근 권한이 없습니다."),

    DUPLICATE_EMAIL("E007", "중복된 이메일입니다."),
    DUPLICATE_NAME("E008", "중복된 이름입니다."),
    EMAIL_NOT_FOUND("E009", "존재하지 않는 이메일입니다."),
    PASSWORD_INCORRECT("E010", "비밀번호가 맞지 않습니다."),
    PASSWORD_FORMAT_INVALID("E011", "비밀번호 형식이 올바르지 않습니다."),
    MAX_TAG_EXCEEDED("E012", "태그는 사용자당 최대 3개까지 저장 가능합니다."),
    NOT_HAVE_TAG("E013", "유저가 해당 태그를 가지고 있지 않습니다."),

    FILE_NOT_FOUND("E014", "파일이 존재하지 않습니다."), // 파일이 존재하지 않습니다.
    FILE_UPLOAD_ERROR("E015", "파일 업로드 중 오류가 발생하였습니다."),

    USER_NOT_FOUND_SPECIFIC("E016", "해당 사용자를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND("E017", "해당 상품을 찾을 수 없습니다."),
    REVIEW_NOT_FOUND("E018", "해당 리뷰를 찾을 수 없습니다."),
    LIKE_NOT_FOUND("E019", "해당 좋아요를 찾을 수 없습니다."),

    SUPPLEMENT_TYPE_NOT_FOUND("E020", "해당 보충제 유형을 찾을 수 없습니다."),
    TOO_MANY_SUPPLEMENT_TYPES("E021", "보충제 유형은 최대 3개까지 입력 가능합니다.");

    private final String code;
    private final String message;
}