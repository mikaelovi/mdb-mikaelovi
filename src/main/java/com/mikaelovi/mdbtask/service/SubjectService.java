package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.dto.SubjectDto;
import com.mikaelovi.mdbtask.entity.Subject;
import com.mikaelovi.mdbtask.repository.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService extends BaseService<Subject, SubjectDto> {

    public SubjectService(SubjectRepository subjectRepository) {
        super(subjectRepository);
    }

    @Override
    Subject convertToEntity(SubjectDto dto) {
        return new Subject(dto.title());
    }

    @Override
    Subject copyToEntity(SubjectDto dto, Subject data) {
        data.setTitle(dto.title());
        return data;
    }

    @Override
    public SubjectDto convertToDto(Subject entity) {
        return new SubjectDto(entity.getId(), entity.getTitle());
    }
}
