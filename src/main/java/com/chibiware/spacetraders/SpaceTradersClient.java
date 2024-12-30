package com.chibiware.spacetraders;


import com.chibiware.spacetraders.model.RegisterResponse;
import com.chibiware.spacetraders.model.ServerStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class SpaceTradersClient {
    private static final String BASE_URL = "https://api.spacetraders.io/v2";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // Store your Bearer token once you have it
    private String token;

    public SpaceTradersClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Get the server status (GET /).
     */
    public ServerStatus getServerStatus() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), ServerStatus.class);
        } else {
            throw new RuntimeException("Failed to get server status. Code: " + response.statusCode());
        }
    }

    /**
     * Register a new agent (POST /register).
     * WARNING: Each new agent symbol must be unique.
     */
    public String registerAgent(String symbol, String faction, String email) throws IOException, InterruptedException {
        String requestBody = """
                {
                  "symbol": "%s",
                  "faction": "%s",
                  "email": "%s"
                }
                """.formatted(symbol, faction, email == null ? "" : email);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201) {
            RegisterResponse registerResponse = objectMapper.readValue(response.body(), RegisterResponse.class);
            this.token = registerResponse.getData().getToken();
            return this.token;
        } else {
            throw new RuntimeException("Registration failed, status: " + response.statusCode()
                    + "\nResponse: " + response.body());
        }
    }

    /**
     * Example of an authenticated GET request: Get your agent details (GET /my/agent).
     */
    public String getMyAgent() throws IOException, InterruptedException {
        ensureToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/my/agent"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to get agent details. Code: " + response.statusCode());
        }
    }

    /**
     * If you already have a token from a previous session, you can set it manually.
     */
    public void setToken(String token) {
        this.token = token;
    }

    private void ensureToken() {
        if (this.token == null || this.token.isBlank()) {
            throw new IllegalStateException("No token set. Please register or set your token first.");
        }
    }
}
