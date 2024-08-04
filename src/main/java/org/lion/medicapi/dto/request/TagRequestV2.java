package org.lion.medicapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TagRequestV2 {

    @NotNull
    private List<String> tags;
}
