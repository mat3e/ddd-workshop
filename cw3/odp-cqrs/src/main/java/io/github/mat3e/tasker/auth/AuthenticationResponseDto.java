package io.github.mat3e.tasker.auth;

class AuthenticationResponseDto {
    private final String token;

    AuthenticationResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
