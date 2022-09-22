package io.github.tuyendev.auth.common.service.authority;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthorityDto implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Boolean status;
}
