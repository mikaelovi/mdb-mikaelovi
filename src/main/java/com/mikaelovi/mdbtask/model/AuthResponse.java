package com.mikaelovi.mdbtask.model;

import java.time.LocalDateTime;

public record AuthResponse(String token, LocalDateTime expiresAt) {
}
