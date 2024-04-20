package com.optimagrowth.context;

import java.util.Objects;

public final class UserContextHolder {
    
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    private UserContextHolder() {
    }

    public static UserContext getContext() {
        UserContext context = userContext.get();
        if (context != null) {
            return context;
        }
        context = createEmptyContext();
        userContext.set(context);
        return context;
    }

    public static void setContext(UserContext context) {
        Objects.requireNonNull(context, "context must not be null");
        userContext.set(context);
    }

    public static UserContext createEmptyContext() {
        return new UserContext();
    }

    public static void removeContext() {
        userContext.remove();
    }

}