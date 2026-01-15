package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;


public class PerfilDTO {
    @NotBlank(message = "El nickname es obligatorio")
    private String nickname;

    private String avatar;

    // getters y setters
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
