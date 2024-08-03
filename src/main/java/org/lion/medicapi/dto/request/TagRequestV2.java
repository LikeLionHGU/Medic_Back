package org.lion.medicapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagRequestV2 {

    @NotNull
    private String tag;
}
