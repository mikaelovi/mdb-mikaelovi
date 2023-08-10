package com.mikaelovi.mdbtask.controller;

import com.mikaelovi.mdbtask.dto.TeacherDto;
import com.mikaelovi.mdbtask.service.TeacherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
@SecurityRequirement(name = "mdb-auth")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.save(teacherDto));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TeacherDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(teacherService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDto> findOne(@PathVariable Integer id) {
        return ResponseEntity.ok(teacherService.findOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDto> update(@PathVariable Integer id, @RequestBody TeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.update(teacherDto, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        teacherService.delete(id);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("count/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> getStudentCountForTeacher(@PathVariable Integer teacherId) {
        return ResponseEntity.ok(teacherService.countStudents(teacherId));
    }
}
