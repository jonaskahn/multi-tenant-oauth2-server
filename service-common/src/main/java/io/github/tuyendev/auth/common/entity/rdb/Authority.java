package io.github.tuyendev.auth.common.entity.rdb;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "authority")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Authority {
    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateId",
            strategy = "io.github.tuyendev.auth.common.entity.UseExistingIdOtherwiseGenerateId")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateId")
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @Builder
    public Authority(String name, String description, Boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authority authority = (Authority) o;
        return id != null && Objects.equals(id, authority.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}