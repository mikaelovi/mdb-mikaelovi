package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.dto.StudentDto;
import com.mikaelovi.mdbtask.entity.Group;
import com.mikaelovi.mdbtask.entity.Student;
import com.mikaelovi.mdbtask.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService extends BaseService<Student, StudentDto> {

    private final StudentRepository studentRepository;
    private final GroupService groupService;

    public StudentService(StudentRepository studentRepository, GroupService groupService) {
        super(studentRepository);
        this.studentRepository = studentRepository;
        this.groupService = groupService;
    }

    @Override
    Student convertToEntity(StudentDto dto) {
        var group = groupService.get(dto.groupId());
        return new Student(dto.firstName(), dto.lastName(), group);
    }

    @Override
    Student copyToEntity(StudentDto dto, Student data) {
        data.setFirstName(dto.firstName());
        data.setLastName(dto.lastName());

        var group = groupService.get(dto.groupId());

        data.setGroup(group);

        return data;
    }

    @Override
    public StudentDto convertToDto(Student entity) {
        return new StudentDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getGroup().getId());
    }

    public List<Student> findAllByGroup(Group group) {
        return studentRepository.findAllByGroup(group);
    }
}
