package com.chibiware.spacetraders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Agent {
    private String symbol;
    private String headquarters;
    private int credits;
    // Possibly more fields exist, depending on the docs.

    public Agent() {}

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getHeadquarters() {
        return headquarters;
    }
    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
}
