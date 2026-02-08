package com.andreafilice.lavorami;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.AlertDialog;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.shimmer.BuildConfig;
import com.google.android.gms.common.api.Result;
import com.google.gson.internal.GsonBuildConfig;

import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountManagement extends AppCompatActivity {
    //*GLOBAL VARIABLES
    SessionManager sessionManager;
    GoogleSignInClient googleClient;
    SupabaseAPI api;
    LinearLayout loginView;
    LinearLayout loggedInView;
    LinearLayout signUpView;
    TextView fullNameTextLoginPage;
    TextView tvProfileName;
    TextView tvProfileEmail;

    private final ActivityResultLauncher<Intent> googleLoginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());

                    try{
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String idToken = account.getIdToken();

                        performSupabaseGoogleLogin(idToken);
                    }
                    catch(ApiException e){
                        Log.e("GOOGLE", "Login fallito. Status Code: " + e.getStatusCode());
                        Toast.makeText(this, "Sign-in con Google fallito.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Log.e("GOOGLE", "Finestra chiusa o annullata. Result Code: " + result.getResultCode());
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ///In this section of the code, we initialize the SessionManager for save the credentials of the Logged Account.
        sessionManager = new SessionManager(this);

        /// In this section of the code, we initialize the GoogleSignInClient for SignIn with Google
        String webClientID = getMetaData("googleAPI");
        Log.d("GOOGLE", webClientID);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientID)
                .requestEmail()
                .build();

        googleClient = GoogleSignIn.getClient(this, gso);

        /// In this section of the code, we initialize the Supabase Server from the keys of the .env file.
        String supabaseURL = getMetaData("supabaseURL");
        if(supabaseURL != null){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(supabaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            api = retrofit.create(SupabaseAPI.class);
        }
        else
            Toast.makeText(this, "Errore durante la connessione al Server.", Toast.LENGTH_SHORT).show();

        //*BACK BUTTON
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            ActivityManager.changeActivity(this, SettingsActivity.class);
        });

        //*SIGN-IN WITH GOOGLE
        CardView btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        btnGoogleLogin.setOnClickListener(v -> {
            if(googleClient != null){
                Intent signInIntent = googleClient.getSignInIntent();

                googleLoginLauncher.launch(signInIntent);
            }
            else{
                Toast.makeText(this, "Errore durante il Login con Google.", Toast.LENGTH_SHORT).show();
            }
        });

        //*VIEWS
        /// In this section we initialize the views for this states: LOGGED IN, SIGN IN, SIGN UP.
        loginView = findViewById(R.id.login_view_container);
        loggedInView = findViewById(R.id.profile_view_container);
        signUpView = findViewById(R.id.signup_view_container);

        //*LOGIN VIEW
        /// In this section of the code, we set the button triggers and more of the SIGN IN View (also called LOGIN).
        TextView tvGoToSignup = findViewById(R.id.tvGoToSignup);
        tvGoToSignup.setOnClickListener(v -> {
            loginView.setVisibility(View.GONE);
            loggedInView.setVisibility(View.GONE);
            signUpView.setVisibility(View.VISIBLE);
        });

        EditText emailLogin = findViewById(R.id.etLoginEmail);
        EditText passwordLogin = findViewById(R.id.etLoginPassword);
        CardView btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            login(emailLogin.getText().toString(), passwordLogin.getText().toString());
            passwordLogin.setText("");
            emailLogin.setText("");
        });

        //*LOGGED IN VIEW
        /// In this section of the code, we set the button triggers and more of the LOGGED IN View.
        fullNameTextLoginPage = findViewById(R.id.tvProfileWelcome);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        CardView btnLogout = findViewById(R.id.btnLogout);
        CardView btnChangePassword = findViewById(R.id.btnChangePassword);
        CardView btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        btnLogout.setOnClickListener(v -> {showConfirmLogout();});
        btnChangePassword.setOnClickListener(v -> {changePassword();});
        btnDeleteAccount.setOnClickListener(v -> {deleteAccount();});

        //*SIGNUP VIEW
        /// In this section of the code, we set the button triggers and more for the SIGN UP View.
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);
        tvGoToLogin.setOnClickListener(v -> {
            loginView.setVisibility(View.VISIBLE);
            loggedInView.setVisibility(View.GONE);
            signUpView.setVisibility(View.GONE);
        });

        EditText emailSignUp = findViewById(R.id.etSignupEmail);
        EditText passwordSignUp = findViewById(R.id.etSignupPassword);
        EditText nameSignUp = findViewById(R.id.etSignupName);
        CardView btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(v -> {
            signUp(nameSignUp.getText().toString(), emailSignUp.getText().toString(), passwordSignUp.getText().toString());
        });

        //*SET THE BASE VIEW
        updateUI();
    }

    private void login(String email, String password) {
        /// In this method, we call from our API of Supabase, the LOGIN action.
        /// This action require some parameters:
        /// @PARAMETERS
        /// @Header apiKey is the ANON key of the Supabase DB from the .env file.
        /// @Body supabaseModels.AuthRequest is the body request for catch errors and log effectualy in the Account.
        /// @PARAMETER of the Method:
        /// String email is the email of the account that we have to log into.
        /// String password is the password of that account to log into.

        SupabaseModels.AuthRequest req = new SupabaseModels.AuthRequest(email, password);

        api.login(getMetaData("supabaseANON"), req).enqueue(new Callback<SupabaseModels.AuthResponse>() {
            @Override
            public void onResponse(Call<SupabaseModels.AuthResponse> call, Response<SupabaseModels.AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().access_token;
                    String email = response.body().user.email;

                    String nameUser = "Utente";

                    if (response.body().user.userMetadata != null) {
                        if (response.body().user.userMetadata.containsKey("full_name"))
                            nameUser = response.body().user.userMetadata.get("full_name").toString();
                        else if (response.body().user.userMetadata.containsKey("name"))
                            nameUser = response.body().user.userMetadata.get("name").toString();
                        else if (response.body().user.userMetadata.containsKey("display_name"))
                            nameUser = response.body().user.userMetadata.get("display_name").toString();
                    }

                    sessionManager.saveSession(token, email, nameUser);

                    updateUI();
                    Toast.makeText(AccountManagement.this, "Bentornato!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(AccountManagement.this, "Errore Login: Credenziali errate", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SupabaseModels.AuthResponse> call, Throwable t) {
                Toast.makeText(AccountManagement.this, "Errore Rete: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performSupabaseGoogleLogin(String token){
        /// In this method, we call from our API the 'loginWithGoogle' function that requires some parameters.
        /// @PARAMETERS
        /// @Header apiKey is the ANON key of the Supabase DB from the .env file.
        /// @Body supabaseModels.GoogleLoginRequest is the body request for catch errors and log effectualy in the Account from Google.
        /// This method call the Google OAuth Client, created on our Google Cloud Console, and contact the Google Servers for retrieve UserDatas.
        /// We take some datas from your account: email, profileImage and DisplayName.
        /// @PARAMETER of the function:
        /// String token is the token from our .env file that contains ClientsID for connecting to Google Cloud services.

        SupabaseModels.GoogleLoginRequest req = new SupabaseModels.GoogleLoginRequest(token);

        api.loginWithGoogle(getMetaData("supabaseANON"), req).enqueue(new Callback<SupabaseModels.AuthResponse>() {
            @Override
            public void onResponse(Call<SupabaseModels.AuthResponse> call, Response<SupabaseModels.AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().access_token;
                    String email = response.body().user.email;

                    String nameUser = "Utente Google";

                    sessionManager.saveSession(token, email, nameUser);

                    updateUI();
                    Toast.makeText(AccountManagement.this, "Accesso con Google riuscito!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AccountManagement.this, "Errore Login: " + response.code(), Toast.LENGTH_SHORT).show();
                    try {
                        Log.e("GOOGLE_LOGIN", response.errorBody().string());
                    } catch (Exception e) {}
                }
            }

            @Override
            public void onFailure(Call<SupabaseModels.AuthResponse> call, Throwable t) {
                Toast.makeText(AccountManagement.this, "Errore di connessione", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUp(String name, String email, String password){
        /// In this method, we call from our API of Supabase, the SIGN-UP action.
        /// This action call the SupabaseURL db and do some POST requests to create the account into the Database.
        /// This action require some parameters:
        /// @PARAMETERS
        /// @Header apiKey is the ANON key of the Supabase DB from the .env file.
        /// @Body supabaseModels.AuthRequest is the body request for catch errors and create an Account.
        /// @PARAMETER of the Method:
        /// String name is the full_name of metadata, is displayed into the LOGGED IN View.
        /// String email is the email of the new account that receives some updates on that mail.
        /// String password is the password to log-in into that account.

        Map<String, Object> userMetadata = new HashMap<>();
        userMetadata.put("full_name", name);

        SupabaseModels.AuthRequest req = new SupabaseModels.AuthRequest(email, password, userMetadata);

        api.signup(getMetaData("supabaseANON"), req).enqueue(new Callback<Void>(){

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AccountManagement.this, "Registrazione COMPLETATA!", Toast.LENGTH_SHORT).show();
                    updateUI();
                }
                else{
                    Toast.makeText(AccountManagement.this, "ERRORE REGISTRAZIONE.", Toast.LENGTH_SHORT).show();
                    Log.d("ERRORE_REG", response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AccountManagement.this, "Errore Rete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showConfirmLogout(){
        /// This method is a bridge one, is called into the 'setOnClickListener' call for popup to the user a Dialog.
        /// If the answer of the user is 'YES', we call the 'logout()' function, otherwise do nothing.

        new AlertDialog.Builder(this)
                .setTitle("Sei sicuro?")
                .setMessage("Sei sicuro di voler uscire dall'Account?")
                .setNegativeButton("Annulla", null)
                .setPositiveButton("Esci", (dialog, which) -> {
                    logout();
                }).show();
    }

    private void logout(){
        /// In this method we call, from the sessionManager, the 'logout()' function.
        /// That function clears the Session cache datas and log-out from the account that is saved into that sessionManager.
        /// We call into tghe 'api.logout' function this sessionManager method.
        /// This action require some parameters:
        /// @PARAMETERS
        /// @Header apiKey is the ANON key of the Supabase DB from the .env file.
        /// @Header Authorization is the SessionBearerToken that let us safely logout the user from his session.

        String token = sessionManager.getToken();

        if(token == null){
            sessionManager.logout();
            return;
        }

        api.logout(getMetaData("supabaseANON"), "Bearer " + token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                sessionManager.logout();
                Toast.makeText(AccountManagement.this, "Account disconnesso!", Toast.LENGTH_SHORT).show();
                updateUI();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AccountManagement.this, "Errore durante la disconnesione", Toast.LENGTH_SHORT).show();
                Log.d("DISCONNECTING_ERROR", t.getMessage());
            }
        });
    }

    private void deleteAccount(){
        /// In this method, we call from our API of Supabase, the DELETE action.
        /// This action call the SupabaseURL db and do an RPC call to the Database for execute an SQL Query.
        /// This action require some parameters:
        /// @PARAMETERS
        /// @Header apiKey is the ANON key of the Supabase DB from the .env file.
        /// @Header Authorization is the SessionBearerToken that let us safely delete the user from his session without touching other user Accounts and
        /// Grant safe permissions.

        new AlertDialog.Builder(this)
                .setTitle("Sei sicuro?")
                .setMessage("Sei sicuro di voler Eliminare Definitivamente il tuo Account?")
                .setNegativeButton("Annulla", null)
                .setPositiveButton("Conferma", (dialog, which) -> {
                    String tokenKey = sessionManager.getToken();

                    if(tokenKey == null){
                        Toast.makeText(this, "Errore: Utente non loggato.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    api.deleteAccount(getMetaData("supabaseANON"), "Bearer " + tokenKey).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                sessionManager.logout();
                                updateUI();
                                Toast.makeText(AccountManagement.this, "Account eliminato correttamente!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AccountManagement.this, "Si è verificato un errore sconosciuto. Riprova", Toast.LENGTH_SHORT).show();
                                try {
                                    Log.e("DELETE_ERR", response.errorBody().string());
                                } catch (Exception e) {}
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(AccountManagement.this, "Si è verificato un errore sconosciuto. Riprova", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).show();
    }

    private void changePassword(){
        /// In this method, we create a Dialog with an EditText field (like LavoraMi for iOS) and, after the Dialog is promped.
        /// We check the userResponse, if is 'YES' we call a function from our Supabase API called 'updatePassword'.
        /// @PARAMETERS
        /// @Header apiKey is the ANON key of the Supabase DB from the .env file.
        /// @Header Authorization is the SessionBearerToken that let us change safely the user Password from our Database.
        /// @Body SupabaseModels.PasswordRequest is the Callback action, declared into our SupabaseModels.java script.
        /// The 'updatePassword' method from our API, get as parameter also the EdiText value from our user.

        final EditText alertInput = new EditText(this);
        alertInput.setHint("Inserisci");
        alertInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        FrameLayout container = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(50, 20, 50, 20);
        alertInput.setLayoutParams(params);
        container.addView(alertInput);

        new AlertDialog.Builder(this)
                .setTitle("Modifica Password")
                .setMessage("Inserisci la tua nuova password per accedere.")
                .setView(container)
                .setNegativeButton("Annulla", null)
                .setPositiveButton("Fine", (dialog, which) -> {
                    String newPassword = alertInput.getText().toString();
                    
                    if(newPassword.length() < 8)
                        Toast.makeText(this, "Errore: Password troppo corta. Minimo 8 caratteri.", Toast.LENGTH_SHORT).show();
                    else{
                        String token = sessionManager.getToken();

                        SupabaseModels.PasswordRequest req = new SupabaseModels.PasswordRequest(newPassword);

                        api.updatePassword(getMetaData("supabaseANON"), "Bearer " + token, req).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(AccountManagement.this, "Password cambiata con successo!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AccountManagement.this, "Errore durante il cambio di Password. Riprova", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(AccountManagement.this, "Errore durante il cambio di Password. Riprova", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }

    private void updateUI(){
        /// This function is a Refactor function to avoid to call svarious times the same snippet of code.
        /// This function set, based from sessionManager value, the UI that the user sees.
        /// If LoggedIn, set the visiblity to GONE for 'loginView' and 'signUpView' variables, after set the values of the user from the sessionManager datas.

        if(sessionManager.isLoggedIn()){
            loginView.setVisibility(View.GONE);
            signUpView.setVisibility(View.GONE);
            loggedInView.setVisibility(View.VISIBLE);

            fullNameTextLoginPage.setText("Ciao " + sessionManager.getUserName());
            tvProfileName.setText(sessionManager.getUserName());
            tvProfileEmail.setText(sessionManager.getUserEmail());
        }
        else{
            loginView.setVisibility(View.VISIBLE);
            signUpView.setVisibility(View.GONE);
            loggedInView.setVisibility(View.GONE);
        }
    }

    private String getMetaData(String key){
        /// This function get from our AndroidManifest.xml the values of .env files.
        /// @PARAMETER
        /// String key is the actual key of the value that we need to grab from the manifest file.

        try{
            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = info.metaData;

            if(bundle != null)
                return bundle.getString(key);
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.d("ERROR", "Impossibile trovare questo valore. ERR: " + e.getMessage());
        }
        return null;
    }
}