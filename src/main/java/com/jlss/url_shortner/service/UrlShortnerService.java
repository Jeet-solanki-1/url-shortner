package com.jlss.url_shortner.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Random;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
@Service
public class UrlShortnerService{

	private final Map<String,String> urlStore = new ConcurrentHashMap<>();

	//Thread-safe in-memory storage
	private final Map<String,UrlMapping> urlStore = new ConcurrentHashMap<>();
	private final Random random = new Random();
	private static final String CH = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final int CODE_LENGTH=6;

	//Inner class to store URL mapping with metadata
	@AllArgsConstructor
	private static class UrlMapping{
		String originalUrl;
		LocalDateTime createdAt;
		LocalDateTime expiresAt;
	
		boolean isExpired(){
			return expiresAt != null && LocalDateTime.now().isAfter(expiresAt)
		}
	}
	public String shortenUrl(String originalUrl, Integer expiryMinutes){
		//todo iMPL IT!
	}
	/**
	 * SELF -CDOES!
	 * Accepts URL as String 
	 * Actions create a hashmap entry (cunccurent)
	
	 String letters = "abcdefghijklmnopqrstuvwxyz";
	int i=0;
	public String shortenUrl(String url){
		String key=String.valueOf(letters.charAt(i%letters.length()));
		urlStore.putIfAbsent(key,url);
		i++;
		System.out.println("i: "+i+"next char/code: "+letters.charAt(i));
		return key;
	}
	public String getOriginal(String code){
		if (urlStore.containsKey(code)){
			return urlStore.get(code);
		}
		else{
			return null;
		}
	}
}