package com.andreafilice.lavorami;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SupabaseModels {

    /// This class is a CallBack class that is called into AccountManagement for do AuthRequests to Supabase server.
    /// @ATTRIBUTES
    /// @String email is the user Email of his account or the account to log into.
    /// @String password is the user Password of his account.
    /// @Map<String,Object> data is the full_name attribute but Supabase accepts only HashMaps, so we have to cast to that type ofd metadata.
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


    /// This class is a Callback succesful class that return user datas from our Database.
    /// @ATTRIBUTES
    /// @String access_token is the token from the LOGIN phase that grant to us the regolar permissions to the user.
    /// @User user is the User Object, it contains: name, email and password.

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

    /// This class is a Callback class for the 'changePassword' method.
    /// @ATTRIBUTES
    /// @String password is the New password from the EditText component into the Dialog in AccountManagement.java
    public static class PasswordRequest {
        public String password;
        public PasswordRequest(String password) {
            this.password = password;
        }
    }


    /// This class is a Callback class from the 'loginWithGoogle' method.
    /// @ATTRIBUTES
    /// @String provider is the LoginProvider to contact for SignIn with that Provider.
    /// @String id_token is the Google Cloud Console Client API token, generated for our Application Client.
    public static class GoogleLoginRequest {
        public String provider = "google";
        public String id_token;

        public GoogleLoginRequest(String token){
            this.id_token = token;
        }
    }
}
