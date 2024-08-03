package org.lion.medicapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeRequestV2 {

    @NotNull
    private Long userId;

    @NotNull
    private Long reviewId;
}
