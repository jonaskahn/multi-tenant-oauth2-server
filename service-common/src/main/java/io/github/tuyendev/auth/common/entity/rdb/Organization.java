package io.github.tuyendev.auth.common.entity.rdb;

import io.github.tuyendev.auth.common.CommonConstants;
import io.github.tuyendev.auth.common.entity.AbstractAuditableEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "organization")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Organization extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 120)
    @NotNull
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Size(max = 255)
    @Column(name = "website")
    private String website;

    @Size(max = 100)
    @Column(name = "country", length = 100)
    private String country;

    @Size(max = 100)
    @Column(name = "state", length = 100)
    private String state;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 100)
    @Column(name = "zone_info", length = 100)
    private String zoneInfo;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @NotNull
    @Column(name = "locked", nullable = false)
    private Boolean locked = false;


    @OneToMany(mappedBy = "organization")
    @ToString.Exclude
    private Set<Department> departments = new LinkedHashSet<>();

    @Builder
    public Organization(Long id, String name, String description, String email, String website,
                        String country, String state, String city, String address, String zoneInfo,
                        Boolean enabled, Boolean locked, Set<Department> departments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.website = website;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
        this.zoneInfo = zoneInfo;
        this.enabled = Objects.requireNonNullElse(enabled, CommonConstants.EntityStatus.ENABLED);
        this.locked = Objects.requireNonNullElse(locked, CommonConstants.EntityStatus.UNLOCKED);
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Organization that = (Organization) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}