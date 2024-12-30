package com.chibiware.spacetraders.model;

public class RegisterResponse {
    private RegisterData data;

    public RegisterResponse() {
    }

    public RegisterData getData() {
        return data;
    }
    public void setData(RegisterData data) {
        this.data = data;
    }

    public static class RegisterData {
        private String token;

        public RegisterData() {
        }

        public String getToken() {
            return token;
        }
        public void setToken(String token) {
            this.token = token;
        }
    }
}
