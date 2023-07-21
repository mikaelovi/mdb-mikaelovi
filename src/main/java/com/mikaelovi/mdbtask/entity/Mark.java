package com.mikaelovi.mdbtask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "marks")
@Getter
@Setter
@ToString
public class Mark extends AbstractEntity {

    @ManyToOne
    private Student student;

    @ManyToOne
    private Subject subject;

    @Column
    private LocalDateTime date;

    @Column(name = "mark")
    private Integer score;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Mark mark = (Mark) o;
        return getId() != null && Objects.equals(getId(), mark.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
