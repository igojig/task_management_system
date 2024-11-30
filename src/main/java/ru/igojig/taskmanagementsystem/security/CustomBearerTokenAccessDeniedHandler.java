package ru.igojig.taskmanagementsystem.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.igojig.taskmanagementsystem.dto.AuthErrorResponse;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomBearerTokenAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    private String realmName;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        log.error(e.getLocalizedMessage(), e);
        Map<String, String> parameters = new LinkedHashMap<>();
        String errorMessage = e.getLocalizedMessage();
        if (Objects.nonNull(realmName)) {
            parameters.put("realm", realmName);
        }
        if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
            errorMessage = "The request requires higher privileges than provided by the access token.";
            parameters.put("error", "insufficient_scope");
            parameters.put("error_description", errorMessage);
            parameters.put("error_uri", "https://tools.ietf.org/html/rfc6750#section-3.1");
        }
        String message = objectMapper.writeValueAsString(AuthErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .timestamp(LocalDateTime.now())
                .error("Access denied")
                .message(errorMessage)
                .path(request.getRequestURI())
                .build());

        String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }

    private static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
        StringBuilder wwwAuthenticate = new StringBuilder();
        wwwAuthenticate.append("Bearer");
        if (!parameters.isEmpty()) {
            wwwAuthenticate.append(" ");
            int i = 0;
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                wwwAuthenticate.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
                if (i != parameters.size() - 1) {
                    wwwAuthenticate.append(", ");
                }
                i++;
            }
        }
        return wwwAuthenticate.toString();
    }

}
