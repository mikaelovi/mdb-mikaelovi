package com.mikaelovi.mdbtask.controller;

import com.mikaelovi.mdbtask.dto.StudentDto;
import com.mikaelovi.mdbtask.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/student")
@SecurityRequirement(name = "mdb-auth")
public class StudentController {
    private final StudentService studentService;

    protected StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.save(studentDto));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<StudentDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(studentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> findOne(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.findOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDto> update(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.update(studentDto, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        studentService.delete(id);
        return ResponseEntity.ok("Ok");
    }
}
