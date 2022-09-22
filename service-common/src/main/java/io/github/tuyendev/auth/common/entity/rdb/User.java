package io.github.tuyendev.auth.common.entity.rdb;

import io.github.tuyendev.auth.common.CommonConstants;
import io.github.tuyendev.auth.common.entity.AbstractAuditableEntity;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User extends AbstractAuditableEntity {
    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateId",
            strategy = "io.github.tuyendev.auth.common.entity.UseExistingIdOtherwiseGenerateId")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateId")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 120)
    @NotNull
    @Column(name = "username", nullable = false, length = 120)
    private String username;

    @Column(name = "preferred_username", length = 16)
    private UUID preferredUsername;

    @Size(max = 120)
    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Size(max = 15)
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "phone_number_verified")
    private Boolean phoneNumberVerified;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    @ToString.Exclude
    private String password;

    @Size(max = 25)
    @Column(name = "family_name", length = 25)
    private String familyName;

    @Size(max = 25)
    @Column(name = "middle_name", length = 25)
    private String middleName;

    @Size(max = 25)
    @Column(name = "given_name", length = 25)
    private String givenName;

    @Size(max = 75)
    @Column(name = "name", length = 75)
    private String name;

    @Size(max = 75)
    @Column(name = "unsigned_name", length = 75)
    private String unsignedName;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @NotNull
    @Column(name = "locked", nullable = false)
    private Boolean locked = false;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    private Department department;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @ToString.Exclude
    private Organization organization;

    @Column(name = "organization_id", insertable = false, updatable = false)
    private Long organizationId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities = new LinkedHashSet<>();

    /**
     * @param username
     * @param preferredUsername   auto generated if missing
     * @param email
     * @param emailVerified
     * @param phoneNumber
     * @param phoneNumberVerified
     * @param password
     * @param familyName
     * @param middleName
     * @param givenName
     * @param enabled
     * @param locked
     * @param gender
     * @param birthdate
     */
    @Builder
    public User(Long id, String username, UUID preferredUsername, String email, Boolean emailVerified,
                String phoneNumber, Boolean phoneNumberVerified, String password, String familyName,
                String middleName, String givenName, Boolean enabled, Boolean locked, Integer gender, LocalDate birthdate) {
        this.id = id;
        this.username = username;
        this.preferredUsername = Objects.requireNonNullElse(preferredUsername, UUID.randomUUID());
        this.email = email;
        this.emailVerified = Objects.requireNonNullElse(emailVerified, CommonConstants.EntityStatus.UNVERIFIED);
        this.phoneNumber = phoneNumber;
        this.phoneNumberVerified = Objects.requireNonNullElse(phoneNumberVerified, CommonConstants.EntityStatus.UNVERIFIED);
        this.password = password;
        this.familyName = familyName;
        this.middleName = middleName;
        this.givenName = givenName;
        this.enabled = Objects.requireNonNullElse(enabled, CommonConstants.EntityStatus.DISABLED);
        this.locked = Objects.requireNonNullElse(locked, CommonConstants.EntityStatus.LOCKED);
        this.gender = Objects.requireNonNullElse(gender, CommonConstants.Gender.UNKNOWN);
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}