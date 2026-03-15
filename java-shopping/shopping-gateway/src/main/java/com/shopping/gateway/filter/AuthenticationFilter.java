package com.shopping.gateway.filter;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret:shopping-cloud-secret-key-must-be-at-least-256-bits-long-for-hs256}")
    private String secret;

    private static final List<String> WHITE_LIST = Arrays.asList(
            "/api/user/login",
            "/api/user/register",
            "/api/user/refresh",
            "/api/product/list",
            "/api/product/search",
            "/api/product/hot",
            "/api/product/new",
            "/api/product/recommend",
            "/api/product/[0-9]+",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/doc.html"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isWhiteListed(path)) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            return unauthorized(exchange, "Missing authorization token");
        }

        if (!token.startsWith("Bearer ")) {
            return unauthorized(exchange, "Invalid token format");
        }

        token = token.substring(7);

        try {
            Claims claims = parseToken(token);
            
            Long userId = claims.get("userId", Long.class);
            String username = claims.getSubject();

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .header("X-Username", username)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
            
        } catch (Exception e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return unauthorized(exchange, "Invalid or expired token");
        }
    }

    private boolean isWhiteListed(String path) {
        return WHITE_LIST.stream().anyMatch(pattern -> {
            if (pattern.contains("*")) {
                return path.matches(pattern.replace("**", ".*"));
            }
            return path.equals(pattern) || path.startsWith(pattern + "/");
        });
    }

    private Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        String body = String.format("{\"code\":401,\"message\":\"%s\",\"data\":null,\"timestamp\":%d}", 
                message, System.currentTimeMillis());
        
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
