package com.andreafilice.lavorami;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SupabaseModels {
    public static class AuthRequest {
        public String email;
        public String password;
        public Map<String, Object> data;
        public AuthRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public AuthRequest(String email, String password, Map<String, Object> data){
            this.email = email;
            this.password = password;
            this.data = data;
        }
    }

    public static class AuthResponse {
        public String access_token;
        public User user;

        public static class User {
            public String id;
            public String email;
            @SerializedName("user_metadata")
            public Map<String, Object> userMetadata;
        }
    }

    public static class PasswordRequest {
        public String password;
        public PasswordRequest(String password) {
            this.password = password;
        }
    }
}
