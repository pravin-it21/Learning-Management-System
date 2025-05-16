package com.cts.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.cts.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil util;

    public static class Config {
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return handleUnauthorized(exchange.getResponse(), "Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    String role = util.extractRolesFromToken(authHeader);
                    String requestedPath = exchange.getRequest().getPath().toString();
                    String method = exchange.getRequest().getMethod().name();

                    if (!isAuthorized(role, requestedPath, method)) {
                        return handleUnauthorized(exchange.getResponse(), "Unauthorized access");
                    }

                } catch (Exception e) {
                    return handleUnauthorized(exchange.getResponse(), "Invalid token");
                }
            }
            return chain.filter(exchange);
        };
    }


//  ("/quiz/**","/users/**","/progress/**","/enroll/**","/course/**")
  private boolean isAuthorized(String role, String path, String method) {
      if ("ADMIN".equalsIgnoreCase(role)) {
          return path.startsWith("/progress") || path.startsWith("/course") || path.startsWith("/enroll") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/enroll") && method.equalsIgnoreCase("PUT") || path.startsWith("/enroll") && method.equalsIgnoreCase("DELETE") || path.startsWith("/quiz")  ;
      } else if ("STUDENT".equalsIgnoreCase(role)) {
          return   path.startsWith("/progress")
        		  || path.startsWith("/course") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/enroll") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/enroll") && method.equalsIgnoreCase("POST")
        		  || path.startsWith("/quiz") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/quiz/submit")
        		  || path.startsWith("/progress");
      }
      else if ("INSTRUCTOR".equalsIgnoreCase(role)) {
          return path.startsWith("/users") || path.startsWith("/progress")
        		  || path.startsWith("/course") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/course") && method.equalsIgnoreCase("POST")
        		  || path.startsWith("/enroll") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/quiz") && method.equalsIgnoreCase("GET")
        		  || path.startsWith("/quiz/create")
        		  || path.startsWith("/progress");
      }
      return false;
  }

    private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}
