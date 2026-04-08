package com.jlss.url_shortner.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Random;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
@Service
public class UrlShortnerService{


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
			return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
		}
	}
	public String shortenUrl(String originalUrl, Integer expiryMinutes){
		//Generate unique short code
		String shortCode = generateUniqueCode();

		//Calculate expiry time
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime expiresAt = expiryMinutes!= null ?
			createdAt.plusMinutes(expiryMinutes) : null;

		// Store mapping
		urlStore.put(shortCode, new UrlMapping(originalUrl,createdAt, expiresAt));

		return shortCode;

	}

	public String getOriginalUrl(String shortCode){
		UrlMapping mapping = urlStore.get(shortCode);
		if (mapping==null){
			return null;
		}
		if (mapping.isExpired()){
			urlStore.remove(shortCode); //Clean up expired entry
			return null;
		}
		return mapping.originalUrl;
	}
	private String generateUniqueCode(){
		StringBuilder code;
		do{
			code = new StringBuilder();
			for (int i=0; i< CODE_LENGTH; i++){
				int index = random.nextInt(CH.length());
				code.append(CH.charAt(index));
			}

		} while(urlStore.containsKey(code.toString())); // Ensure uniquness
		return code.toString();
	}

	// Optional:Get stats for a short code
	public Map<String, Object> getStats(String shortCode){
		UrlMapping mapping = urlStore.get(shortCode);
		if (mapping==null){
			return null;
		}
		return Map.of(
			"originalUrl", mapping.originalUrl,
			"createdAt", mapping.createdAt,
			"expiresAt", mapping.expiresAt,
			"isExpired", mapping.isExpired()
		);
	}

	// Optional: Delete a short code
	public boolean deleteShortCode(String shortCode){
		return urlStore.remove(shortCode) != null;
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
	*/

}