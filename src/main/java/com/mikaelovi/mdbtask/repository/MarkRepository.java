package com.mikaelovi.mdbtask.repository;

import com.mikaelovi.mdbtask.entity.Mark;
import com.mikaelovi.mdbtask.entity.Student;
import com.mikaelovi.mdbtask.entity.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepository extends BaseRepository<Mark> {
    List<Mark> findMarksByStudent(Student student);

    List<Mark> findMarksBySubject(Subject subject);
}
