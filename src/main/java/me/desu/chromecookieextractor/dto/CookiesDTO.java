package me.desu.chromecookieextractor.dto;

import lombok.Data;

@Data
public class CookiesDTO {
    private String hostKey;
    private String topFrameSiteKey;
    private String name;
    private String path;
    private String value;
}
