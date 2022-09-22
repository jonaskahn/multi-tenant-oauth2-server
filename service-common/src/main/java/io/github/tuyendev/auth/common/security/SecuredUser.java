package io.github.tuyendev.auth.common.security;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface SecuredUser extends Serializable {

    Long getId();

    String getUsername();

    UUID getPreferredUsername();

    String getEmail();

    Boolean getEmailVerified();

    String getPhoneNumber();

    Boolean getPhoneNumberVerified();

    String getPassword();

    String getFamilyName();

    String getMiddleName();

    String getGivenName();

    String getName();

    String getUnsignedName();

    Boolean getEnabled();

    Boolean getLocked();

    Integer getGender();

    LocalDate getBirthdate();

    Set<String> getAuthoritiesName();
}
