package com.optimagrowth.http;

import lombok.Data;

@Data
public final class HeaderNames {
    
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private HeaderNames() {
    }

}