package com.optimagrowth.context;

import lombok.Data;

@Data
public class UserContext {

    private String correlationId;
    private String authToken;
    private String userId;
    private String organizationId;

}