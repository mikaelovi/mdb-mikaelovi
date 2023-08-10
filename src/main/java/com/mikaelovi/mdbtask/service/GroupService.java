package com.mikaelovi.mdbtask.service;

import com.mikaelovi.mdbtask.dto.GroupDto;
import com.mikaelovi.mdbtask.entity.Group;
import com.mikaelovi.mdbtask.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService extends BaseService<Group, GroupDto> {

    public GroupService(GroupRepository groupRepository) {
        super(groupRepository);
    }

    @Override
    Group convertToEntity(GroupDto dto) {
        return new Group(dto.name());
    }

    @Override
    Group copyToEntity(GroupDto dto, Group data) {
        data.setName(dto.name());
        return data;
    }

    @Override
    public GroupDto convertToDto(Group entity) {
        return new GroupDto(entity.getId(), entity.getName());
    }
}
