package com.bacanas.cadastro.requests;

public record LoginResponse(String accessToken, Long expiresIn) {
}