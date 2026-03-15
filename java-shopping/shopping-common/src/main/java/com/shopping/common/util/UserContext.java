package com.shopping.common.util;

import lombok.Data;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserContext implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

    private Long userId;
    private String username;
    private Set<String> roles;
    private String token;

    public static UserContext getCurrentUser() {
        return CONTEXT.get();
    }

    public static void setCurrentUser(UserContext userContext) {
        CONTEXT.set(userContext);
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static Long getCurrentUserId() {
        UserContext context = getCurrentUser();
        return context != null ? context.getUserId() : null;
    }

    public static String getCurrentUsername() {
        UserContext context = getCurrentUser();
        return context != null ? context.getUsername() : null;
    }

    public static boolean hasRole(String role) {
        UserContext context = getCurrentUser();
        return context != null && context.getRoles() != null && context.getRoles().contains(role);
    }

    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }
}
