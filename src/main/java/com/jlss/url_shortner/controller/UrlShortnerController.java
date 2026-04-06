package com.jlss.url_shortner.controller;

import org.springframework.web.bind.annotation.*;
import com.jlss.url_shortner.service.UrlShortnerService;
import org.springframework.web.servlet.view.RedirectView;
@RestController
@CrossOrigin(origins = "*")
public class UrlShortnerController{
	private final UrlShortnerService service;
	public UrlShortnerController(UrlShortnerService service){
		this.service=service;
	}

	@GetMapping("/api/original/")
	public String shortenUrl(@RequestParam String url){
		String resp = service.shortenUrl(url);
		if (resp==null){
			return "url for this code Not found or (get updated by another url as we only support 26 urls in our server)";
		}
		return resp;
	}
	@GetMapping("/api/{code}")
	public RedirectView getOriginal(@PathVariable String code){
		return new RedirectView(service.getOriginal(code));
	}

}