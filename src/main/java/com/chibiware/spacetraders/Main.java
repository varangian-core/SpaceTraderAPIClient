package com.chibiware.spacetraders;

import com.chibiware.spacetraders.model.ServerStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1. Read properties from System.getProperty(...) (passed by Gradle)
        String agentSymbol = System.getProperty("agentSymbol", "DEFAULT_SYMBOL");
        String faction = System.getProperty("faction", "COSMIC");
        String email = System.getProperty("email", "");
        String accountExists = System.getProperty("accountExists", "false");
        String agentToken = System.getProperty("agentToken", "");

        System.out.println("==== SpaceTraders Setup ====");
        System.out.println("agentSymbol = " + agentSymbol);
        System.out.println("faction     = " + faction);
        System.out.println("email       = " + email);
        System.out.println("accountExists = " + accountExists);
        System.out.println("agentToken  = " + (agentToken.isBlank() ? "(not set)" : agentToken));

        SpaceTradersClient client = new SpaceTradersClient();

        try {
            // 2) Check server status
            ServerStatus status = client.getServerStatus();
            System.out.println("Server status: " + status.getStatus());
            System.out.println("Server version: " + status.getVersion());

            // 3) If the account doesn't exist yet, attempt to register
            if (!Boolean.parseBoolean(accountExists)) {
                System.out.println("\nAccount does NOT exist -> Registering a new agent...");
                String newToken = client.registerAgent(agentSymbol, faction, email);

                System.out.println("Registration succeeded, token: " + newToken);

                // 3.1 Update gradle.properties with accountExists=true and agentToken=newToken
                updateGradleProperties("accountExists", "true");
                updateGradleProperties("agentToken", newToken);

                // 3.2 We'll use the new token going forward
                agentToken = newToken;
            } else {
                // If accountExists=true, assume we already have a valid token in gradle.properties
                System.out.println("\nAccount already exists. Using existing token.");
            }

            // 4) Set the token in the client (either newly created or from gradle.properties)
            if (agentToken != null && !agentToken.isBlank()) {
                client.setToken(agentToken);

                // 5) Example: Call an authenticated endpoint
                System.out.println("\nFetching agent details with token...");
                String agentDetails = client.getMyAgent();
                System.out.println("Agent details:\n" + agentDetails);
            } else {
                System.out.println("No valid token available. Cannot make authenticated requests.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates (or inserts) a property key=value in gradle.properties.
     * This method:
     *  1) Reads all lines from gradle.properties.
     *  2) Locates the line that starts with key=..., replaces it.
     *  3) If not found, appends it.
     *  4) Writes lines back to gradle.properties.
     */
    private static void updateGradleProperties(String key, String value) {
        File propFile = new File("gradle.properties");
        if (!propFile.exists()) {
            System.out.println("WARNING: gradle.properties not found. Cannot update " + key);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(propFile.toPath());
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(key + "=")) {
                    lines.set(i, key + "=" + value);
                    found = true;
                    break;
                }
            }
            if (!found) {
                lines.add(key + "=" + value);
            }
            Files.write(propFile.toPath(), lines);
            System.out.println("Updated gradle.properties: " + key + "=" + value);
        } catch (IOException e) {
            System.out.println("Failed to update gradle.properties for key=" + key + ". " + e.getMessage());
        }
    }
}
