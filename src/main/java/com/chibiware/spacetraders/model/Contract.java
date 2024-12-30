package com.chibiware.spacetraders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contract {
    private String id;
    private boolean accepted;
    private boolean fulfilled;

    public Contract() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public boolean isAccepted() {
        return accepted;
    }
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
}
