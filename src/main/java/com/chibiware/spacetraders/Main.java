package com.chibiware.spacetraders;

import com.chibiware.spacetraders.model.ServerStatus;


public class Main {
    public static void main(String[] args) {
        SpaceTradersClient client = new SpaceTradersClient();
        try {
            // 1) Check server status
            ServerStatus status = client.getServerStatus();
            System.out.println("Server status: " + status.getStatus());
            System.out.println("Server version: " + status.getVersion());

            // 2) Register your agent
            //    WARNING: This can only be done once per reset or with a new unique symbol each time.
            String token = client.registerAgent("MYAGENT123", "COSMIC", null);
            System.out.println("Registration succeeded, token: " + token);

            // 3) Call an authenticated endpoint
            String agentDetails = client.getMyAgent();
            System.out.println("Agent details:\n" + agentDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
