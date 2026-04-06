package com.jlss.url_shortner.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class UrlShortnerService{

	private final Map<String,String> urlStore = new ConcurrentHashMap<>();


	/**
	 * 
	 * Accepts URL as String 
	 * Actions create a hashmap entry (cunccurent)
	 * Returns the short code as string
	 * */
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