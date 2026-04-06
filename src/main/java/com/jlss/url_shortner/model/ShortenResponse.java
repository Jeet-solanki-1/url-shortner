package com .jlss.url_shortner.model;

import lombok.*;
import java.time.LocalDateTime;

@Data 
@AllArgsConstructor
public class ShortenResponse{
	private String shortCode;
	private String shortUrl;
	private String originalUrl;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
}