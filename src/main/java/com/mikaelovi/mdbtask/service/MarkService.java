package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.dto.MarkDto;
import com.mikaelovi.mdbtask.entity.Mark;
import com.mikaelovi.mdbtask.repository.MarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarkService extends BaseService<Mark, MarkDto> {
    private final MarkRepository markRepository;
    private final StudentService studentService;
    private final SubjectService subjectService;

    public MarkService(MarkRepository markRepository, StudentService studentService, SubjectService subjectService) {
        super(markRepository);
        this.markRepository = markRepository;
        this.studentService = studentService;
        this.subjectService = subjectService;
    }

    @Override
    Mark convertToEntity(MarkDto dto) {
        var student = studentService.get(dto.studentId());
        var subject = subjectService.get(dto.subjectId());

        return new Mark(student, subject, dto.date(), dto.score());
    }

    @Override
    Mark copyToEntity(MarkDto dto, Mark data) {
        data.setDate(dto.date());
        data.setScore(dto.score());

        return data;
    }

    @Override
    public MarkDto convertToDto(Mark entity) {
        return new MarkDto(entity.getId(), entity.getDate(), entity.getScore(), entity.getStudent().getId(), entity.getSubject().getId());
    }

    public List<MarkDto> getMarksForStudent(Integer studentId) {
        var student = studentService.get(studentId);

        var markList = markRepository.findMarksByStudent(student);

        return markList.stream().map(this::convertToDto).toList();
    }

    private MarkDto getMarksForSubject(Integer subjectId, Integer studentId) {
        var subject = subjectService.get(subjectId);
        var student = studentService.get(studentId);

        var markList = markRepository.findMarksBySubjectAndStudent(subject, student);

        return markList.stream().map(this::convertToDto).findFirst().orElse(null);
    }

    public List<Map<String, MarkDto>> getStudentMarksForAllSubjects(Integer studentId) {

        var markList = getMarksForStudent(studentId);

        return List.of(markList.stream().collect(Collectors.toMap(markDto -> subjectService.get(markDto.subjectId()).getTitle(),
                markDto -> getMarksForSubject(markDto.subjectId(), studentId))));
    }
}
