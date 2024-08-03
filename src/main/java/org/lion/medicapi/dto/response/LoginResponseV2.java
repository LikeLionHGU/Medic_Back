package org.lion.medicapi.dto.response;

import lombok.Data;
import org.lion.medicapi.type.TagType;

import java.util.List;

@Data
public class LoginResponseV2 {

    private Long id;
    private String name;
    private List<TagType> tags;
}
