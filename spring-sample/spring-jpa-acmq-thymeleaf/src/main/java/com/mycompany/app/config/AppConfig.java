package com.mycompany.app.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="app")
public class AppConfig {

	private List<String> list;
	
	private Map<String,Object> map;
	
	private List<Map<String,String>> complexList;
	
	private  Map<String,List<String>> complexMap;

	public List<String> getList() {
		return list;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<Map<String, String>> getComplexList() {
		return complexList;
	}

	public void setComplexList(List<Map<String, String>> complexList) {
		this.complexList = complexList;
	}

	public Map<String, List<String>> getComplexMap() {
		return complexMap;
	}

	public void setComplexMap(Map<String, List<String>> complexMap) {
		this.complexMap = complexMap;
	}
	
	
	
	
}
