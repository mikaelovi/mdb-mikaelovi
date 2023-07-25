package com.mikaelovi.mdbtask.controller;

import com.mikaelovi.mdbtask.dto.MarkDto;
import com.mikaelovi.mdbtask.service.MarkService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mark")
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarkDto> create(@RequestBody MarkDto markDto) {
        return ResponseEntity.ok(markService.save(markDto));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<MarkDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(markService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarkDto> findOne(@PathVariable Integer id) {
        return ResponseEntity.ok(markService.findOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarkDto> update(@PathVariable Integer id, @RequestBody MarkDto markDto) {
        return ResponseEntity.ok(markService.update(markDto, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        markService.delete(id);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/all/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MarkDto>> getMarksForStudent(@PathVariable Integer studentId) {
        return ResponseEntity.ok(markService.getMarksForStudent(studentId));
    }

    @GetMapping("/all-subjects/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<Integer, List<MarkDto>>> getMarksForAllSubjects(@PathVariable Integer studentId) {
        return ResponseEntity.ok(markService.getStudentMarksForAllSubjects(studentId));
    }
}
