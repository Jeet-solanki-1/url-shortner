package com.jlss.url_shortner.model;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO{
	@NotBlank(message = "URL cannot be empty")
	@Pattern(regexp = "^(http|https)://.*$",message="URL must start with http:// or https://")
	private String url;
	private Integer expiryMinutes;
}
