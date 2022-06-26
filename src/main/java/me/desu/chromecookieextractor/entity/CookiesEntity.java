package me.desu.chromecookieextractor.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "Cookies")
public class CookiesEntity {

    @Id
    private long creationUtc;
    private String hostKey;
    private String topFrameSiteKey;
    private String name;
    private String value;
    private byte[] encryptedValue;
    private String path;
    private long expiresUtc;
    private long isSecure;
    private long isHttponly;
    private long lastAccessUtc;
    private long hasExpires;
    private long isPersistent;
    private long priority;
    private long samesite;
    private long sourceScheme;
    private long sourcePort;
    private long isSameParty;
    private long lastUpdateUtc;
}
