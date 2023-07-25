package com.mikaelovi.mdbtask.repository;

import com.mikaelovi.mdbtask.entity.Group;
import com.mikaelovi.mdbtask.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends BaseRepository<Student> {
    List<Student> findAllByGroup(Group group);
}
