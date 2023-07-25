package com.mikaelovi.mdbtask.controller;

import com.mikaelovi.mdbtask.dto.GroupDto;
import com.mikaelovi.mdbtask.service.GroupService;
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
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;


    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDto> create(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(groupService.save(groupDto));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GroupDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(groupService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDto> findOne(@PathVariable Integer id) {
        return ResponseEntity.ok(groupService.findOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GroupDto> update(@PathVariable Integer id, @RequestBody GroupDto updated) {
        return ResponseEntity.ok(groupService.update(updated, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        groupService.delete(id);
        return ResponseEntity.ok("Ok");
    }
}
