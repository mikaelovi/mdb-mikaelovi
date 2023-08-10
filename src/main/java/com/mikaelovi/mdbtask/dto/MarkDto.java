package com.mikaelovi.mdbtask.dto;

import java.time.LocalDateTime;

public record MarkDto(Integer id, LocalDateTime date, int score, Integer studentId, Integer subjectId) implements BaseDto {
}
