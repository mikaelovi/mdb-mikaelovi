package com.mikaelovi.mdbtask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorPayload {
    private String code;
    private String msg;
}
