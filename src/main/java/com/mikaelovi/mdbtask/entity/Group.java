package com.mikaelovi.mdbtask.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "groups")
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Group extends BaseEntity {

    @NonNull
    @Column(unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return getId() != null && Objects.equals(getId(), group.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
