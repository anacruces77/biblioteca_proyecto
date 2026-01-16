package com.example.biblioteca.dto;


// Me sirve para el test
public class PerfilResponseDTO {

    private Long id;
    private String nickname;
    private String avatar;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
