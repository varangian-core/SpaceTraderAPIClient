package com.chibiware.spacetraders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse {
    private RegisterData data;

    public RegisterResponse() {}

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RegisterData {
        private Agent agent;
        private Contract contract;
        private Faction faction;
        private Ship ship;
        private String token;

        public RegisterData() {}

        public Agent getAgent() {
            return agent;
        }

        public void setAgent(Agent agent) {
            this.agent = agent;
        }

        public Contract getContract() {
            return contract;
        }

        public void setContract(Contract contract) {
            this.contract = contract;
        }

        public Faction getFaction() {
            return faction;
        }

        public void setFaction(Faction faction) {
            this.faction = faction;
        }

        public Ship getShip() {
            return ship;
        }

        public void setShip(Ship ship) {
            this.ship = ship;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
