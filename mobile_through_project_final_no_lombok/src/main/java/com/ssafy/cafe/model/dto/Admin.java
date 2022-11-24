package com.ssafy.cafe.model.dto;

public class Admin {
    Integer id;
    String token;
    @Override
	public String toString() {
		return "Admin [id=" + id + ", token=" + token + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Admin(Integer id, String token){
        this.id = id;
        this.token = token;
    }
}
