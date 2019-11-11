package com.example.demo.entity;


import java.time.LocalDateTime;

public class Token {
    private Long tokenId;
    private String token;
    private AppUser appUser;
    private LocalDateTime createAt;


    public Token(String token, AppUser appUser) {
        this.token = token;
        this.appUser = appUser;
    }

    public Token(Long tokenId, LocalDateTime createAt) {
        this.tokenId = tokenId;
        this.createAt = createAt;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}

