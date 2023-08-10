package com.mikaelovi.mdbtask.dto;

public record StudentDto(Integer id, String firstName, String lastName, Integer groupId) implements BaseDto {}
