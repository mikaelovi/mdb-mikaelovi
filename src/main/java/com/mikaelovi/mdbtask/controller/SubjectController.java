package com.mikaelovi.mdbtask.controller;

import com.mikaelovi.mdbtask.dto.SubjectDto;
import com.mikaelovi.mdbtask.service.SubjectService;
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
@RequestMapping("/subject")
@SecurityRequirement(name = "mdb-auth")
public class SubjectController {
    private final SubjectService subjectService;


    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectDto> create(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.save(subjectDto));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<SubjectDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(subjectService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectDto> findOne(@PathVariable Integer id) {
        return ResponseEntity.ok(subjectService.findOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectDto> update(@PathVariable Integer id, @RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.update(subjectDto, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        subjectService.delete(id);
        return ResponseEntity.ok("Ok");
    }
}
