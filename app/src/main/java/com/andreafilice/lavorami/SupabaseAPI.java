package com.andreafilice.lavorami;

import retrofit2.http.Header;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.Call;

public interface SupabaseAPI {
    @POST("auth/v1/signup")
    Call<Void> signup(
        @Header("apikey") String apiKey,
        @Body SupabaseModels.AuthRequest request
    );

    @POST("auth/v1/token?grant_type=password")
    Call<SupabaseModels.AuthResponse> login(
        @Header("apikey") String apiKey,
        @Body SupabaseModels.AuthRequest request
    );

    @POST("auth/v1/logout")
    Call<Void> logout(
        @Header("apikey") String apiKey,
        @Header("Authorization") String bearerToken
    );

    @PUT("auth/v1/user")
    Call<Void> updatePassword(
        @Header("apikey") String apiKey,
        @Header("Authorization") String bearerToken,
        @Body SupabaseModels.PasswordRequest request
    );

    @POST("rest/v1/rpc/delete_self")
    Call<Void> deleteAccount(
        @Header("apikey") String apiKey,
        @Header("Authorization") String bearerToken
    );

    @POST("auth/v1/token?grant_type=id_token")
    Call<SupabaseModels.AuthResponse> loginWithGoogle(
            @Header("apikey") String apiKey,
            @Body SupabaseModels.GoogleLoginRequest request
    );
}
