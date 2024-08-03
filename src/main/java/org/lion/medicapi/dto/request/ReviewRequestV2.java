package org.lion.medicapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestV2 {

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer star;
    private String contents;

    @NotNull
    private String purchaseDate;

    @NotNull
    private String reviewDate;
}