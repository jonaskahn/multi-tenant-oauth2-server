package io.github.tuyendev.auth.common.service.userinfo;

import io.github.tuyendev.auth.common.entity.rdb.Department;
import io.github.tuyendev.auth.common.entity.rdb.Organization;
import io.github.tuyendev.auth.common.security.SecuredUser;
import io.github.tuyendev.auth.common.service.authority.AuthorityDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import one.util.streamex.StreamEx;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserInfoDto implements SecuredUser {

    private Long id;

    private String username;

    private UUID preferredUsername;

    private String email;

    private Boolean emailVerified;

    private String phoneNumber;

    private Boolean phoneNumberVerified;

    @ToString.Exclude
    private String password;

    private String familyName;

    private String middleName;

    private String givenName;

    private String name;

    private String unsignedName;

    private Boolean enabled;

    private Boolean locked;

    private Integer gender;

    private LocalDate birthdate;

    private Department department;

    private Organization organization;

    private Set<AuthorityDto> authorities;

    @Override
    public Set<String> getAuthoritiesName() {
        if (Objects.isNull(authorities)) return Set.of();
        return StreamEx.of(authorities)
                .map(AuthorityDto::getName)
                .toImmutableSet();
    }
}
