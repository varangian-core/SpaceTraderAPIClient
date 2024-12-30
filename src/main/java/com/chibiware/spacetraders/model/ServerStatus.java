package com.chibiware.spacetraders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerStatus {
    private String status;
    private String version;
    private String resetDate;

    public ServerStatus() {}

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getResetDate() {
        return resetDate;
    }
    public void setResetDate(String resetDate) {
        this.resetDate = resetDate;
    }
}
