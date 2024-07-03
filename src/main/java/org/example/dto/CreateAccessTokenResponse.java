package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenResponse {
    private String accessToken;

    public CreateAccessTokenResponse(String newAccessToken) {
        this.accessToken = newAccessToken;
    }
}
