package com.mikaelovi.mdbtask.repository;

import com.mikaelovi.mdbtask.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
