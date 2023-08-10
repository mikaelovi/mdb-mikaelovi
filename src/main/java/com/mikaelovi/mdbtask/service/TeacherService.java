package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.dto.TeacherDto;
import com.mikaelovi.mdbtask.entity.Teacher;
import com.mikaelovi.mdbtask.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends BaseService<Teacher, TeacherDto> {
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public TeacherService(TeacherRepository teacherRepository, GroupService groupService, SubjectService subjectService, StudentService studentService) {
        super(teacherRepository);
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

    @Override
    Teacher convertToEntity(TeacherDto dto) {
        var group = groupService.get(dto.groupId());
        var subject = subjectService.get(dto.subjectId());

        return new Teacher(subject, group);
    }

    @Override
    Teacher copyToEntity(TeacherDto dto, Teacher data) {
        var group = groupService.get(dto.groupId());
        var subject = subjectService.get(dto.subjectId());

        data.setGroup(group);
        data.setSubject(subject);
        return data;
    }

    @Override
    public TeacherDto convertToDto(Teacher entity) {
        return new TeacherDto(entity.getId(), entity.getSubject().getId(), entity.getGroup().getId());
    }

    public Integer countStudents(Integer teacherId) {
        var teacher = get(teacherId);
        var group = teacher.getGroup();

        var studentList = studentService.findAllByGroup(group);

        return studentList.size();
    }
}
