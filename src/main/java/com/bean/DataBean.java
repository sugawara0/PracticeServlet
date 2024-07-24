package com.bean;

public class DataBean {
	private int id;
	private String name;
	private String value;
	
	public DataBean(int id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
	}
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}

}
