package com.jlss.url_shortner.controller;

import org.springframework.web.bind.annotation.*;
import com.jlss.url_shortner.service.UrlShortnerService;
import org.springframework.http.*;
import com.jlss.url_shortner.model.*;
import java.time.LocalDateTime;
import java.net.URI;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class UrlShortnerController{
	private final UrlShortnerService service;
	public UrlShortnerController(UrlShortnerService service){
		this.service=service;
	}

	@PostMapping("/api/shorten/")
	public ResponseEntity<ShortenResponse> shortenUrl(@Valid @RequestBody RequestDTO req){
		String code = service.shortenUrl(req.getUrl(),
		req.getExpiryMinutes()
	);
		String shortUrl = "http://localhost:8080/"+code;
		ShortenResponse resp = new ShortenResponse(
			code,shortUrl,req.getUrl(),
			LocalDateTime.now(),
			req.getExpiryMinutes()!=null ?
				LocalDateTime.now().plusMinutes(req.getExpiryMinutes()): null
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}
	@GetMapping("/{code}")
	public ResponseEntity<Void> getOriginal(@PathVariable String code){
		String originalUrl = service.getOriginalUrl(code);
		if (originalUrl==null){
			return ResponseEntity.notFound().build();
		}

		HttpHeaders h = new HttpHeaders();
		h.setLocation(URI.create(originalUrl));
		return ResponseEntity.status(HttpStatus.FOUND).headers(h).build();
	}


}