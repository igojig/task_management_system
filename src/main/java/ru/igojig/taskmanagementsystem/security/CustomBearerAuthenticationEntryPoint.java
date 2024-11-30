package ru.igojig.taskmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
public class CustomBearerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    private String realmName;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        log.error(e.getLocalizedMessage(), e);
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String errorMessage = "Insufficient authentication details";
        Map<String, String> parameters = new LinkedHashMap<>();
        if (Objects.nonNull(realmName)) {
            parameters.put("realm", realmName);
        }
        if (e instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) e).getError();
            parameters.put("error", error.getErrorCode());
            if (StringUtils.hasText(error.getDescription())) {
                errorMessage = error.getDescription();
                parameters.put("error_description", errorMessage);
            }
            if (StringUtils.hasText(error.getUri())) {
                parameters.put("error_uri", error.getUri());
            }
            if (error instanceof BearerTokenError bearerTokenError) {
                if (StringUtils.hasText(bearerTokenError.getScope())) {
                    parameters.put("scope", bearerTokenError.getScope());
                }
                status = bearerTokenError.getHttpStatus();
            }
        }

        String jsonResponse = objectMapper
                .writeValueAsString(AuthErrorResponse.builder()
                        .status(status.value())
                        .timestamp(LocalDateTime.now())
                        .error("Unauthenticated")
                        .message(errorMessage)
                        .path(request.getRequestURI())
                        .build());


        String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
        response.addHeader("WWW-Authenticate", wwwAuthenticate);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
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
