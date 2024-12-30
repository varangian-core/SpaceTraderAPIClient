package com.chibiware.spacetraders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Faction {
    private String symbol;
    private String name;
    private String description;
    private String headquarters;
    private List<FactionTrait> traits; // if you want to parse traits

    public Faction() {}

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadquarters() {
        return headquarters;
    }
    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public List<FactionTrait> getTraits() {
        return traits;
    }
    public void setTraits(List<FactionTrait> traits) {
        this.traits = traits;
    }
}
