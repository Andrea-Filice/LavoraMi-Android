package com.andreafilice.lavorami;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.cardview.widget.CardView;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

public class AdvancedOptions extends AppCompatActivity {

    private static final String BIOMETRIC_KEY_ALIAS = "AdvancedOptionsBiometricKey";

    private void generateSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        if (keyStore.containsAlias(BIOMETRIC_KEY_ALIAS)) {
            return;
        }
        KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                BIOMETRIC_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                .setInvalidatedByBiometricEnrollment(true)
                .build();
        KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        keyGenerator.init(keyGenParameterSpec);
        keyGenerator.generateKey();
    }

    private SecretKey getSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        return (SecretKey) keyStore.getKey(BIOMETRIC_KEY_ALIAS, null);
    }

    private Cipher getCipher() throws Exception {
        Cipher cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/" +
                        KeyProperties.BLOCK_MODE_CBC + "/" +
                        KeyProperties.ENCRYPTION_PADDING_PKCS7);
        SecretKey secretKey = getSecretKey();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advanced_options);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //*BUTTONS
        ImageButton backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> {ActivityManager.changeActivity(this, SettingsActivity.class);});

        //*LOAD DATAS
        /// Load the value of the Switch from the DataManager.
        boolean isErrorActive = DataManager.getBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, false);
        boolean isBannerActive = DataManager.getBoolData(this, DataKeys.KEY_SHOW_BANNERS, true);
        boolean isRequireBiometrics = DataManager.getBoolData(this, DataKeys.KEY_REQUIRE_BIOMETRICS, true);
        boolean isShowDetails = DataManager.getBoolData(this, DataKeys.KEY_SHOW_DETAILS, true);

        Switch errorMessagesSwitch = findViewById(R.id.switchErrors);
        Switch strikeBannersSwitch = findViewById(R.id.switchBanner);
        Switch biometricsSwitch = findViewById(R.id.switchBiometrics);
        Switch detailsSwitch = findViewById(R.id.switchDetails);

        errorMessagesSwitch.setChecked(isErrorActive);
        errorMessagesSwitch.setTrackTintMode((errorMessagesSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        strikeBannersSwitch.setChecked(isBannerActive);
        strikeBannersSwitch.setTrackTintMode((strikeBannersSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        biometricsSwitch.setChecked(isRequireBiometrics);
        biometricsSwitch.setTrackTintMode((biometricsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        detailsSwitch.setChecked(isShowDetails);
        detailsSwitch.setTrackTintMode((detailsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);

        //*SAVE DATAS
        /// Save the value from the Switch Checked status to DataManager.
        errorMessagesSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_SHOW_ERROR_MESSAGES, errorMessagesSwitch.isChecked());
            errorMessagesSwitch.setTrackTintMode((errorMessagesSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });

        strikeBannersSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_SHOW_BANNERS, strikeBannersSwitch.isChecked());
            strikeBannersSwitch.setTrackTintMode((strikeBannersSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });

        biometricsSwitch.setOnClickListener(v -> {showBiometricPrompt(biometricsSwitch);});

        detailsSwitch.setOnClickListener(v -> {
            DataManager.saveBoolData(this, DataKeys.KEY_SHOW_DETAILS, detailsSwitch.isChecked());
            detailsSwitch.setTrackTintMode((detailsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        });

        //*CACHE MEMORY
        CardView btnCacheMemory = findViewById(R.id.btnCacheMemory);
        btnCacheMemory.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Sei sicuro?")
                    .setMessage("Sei sicuro di voler eliminare la memoria Cache dell'app?")
                    .setNegativeButton("Annulla", null)
                    .setPositiveButton("Conferma", (dialog, which) -> {
                        deleteCache(this);
                    }).show();
        });
    }

    public static void deleteCache(Context context) {
        /// In this method, we catch from the Context the current CacheDirectory.
        /// @PARAMETERS
        /// Context context is the Activity from take the Directory Path to the Cache Memory.

        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
            Toast.makeText(context, "Cache pulite correttamente!", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        /// In this method, we delete effectually the Cache memory Partition.
        /// @PARAMETERS
        /// File dir is the directory to delete (Cache Directory in this case).

        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();

            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));

                if (!success)
                    return false;
            }

            return dir.delete();
        }
        else if(dir!= null && dir.isFile())
            return dir.delete();
        else
            return false;
    }

    private void showBiometricPrompt(Switch biometricsSwitch) {
        /// This method call a Google API to request a Biometric authentication.
        /// This method protects the user protection by veryfing who is accesing at that setting with that device.
        /// @FALLBACK rules:
        /// If no FaceID or TouchID are setted-up into the device, Fallback to ask the PIN or Device Password.
        /// If no PIN also is found, Fallback to unlock the screen automatically.

        BiometricManager biometricManager = BiometricManager.from(this);

        int authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL;
        int canAuthenticate = biometricManager.canAuthenticate(authenticators);
        biometricsSwitch.setChecked(DataManager.getBoolData(this, DataKeys.KEY_REQUIRE_BIOMETRICS, true));

        if (canAuthenticate != BiometricManager.BIOMETRIC_SUCCESS) {
            DataManager.saveBoolData(this, DataKeys.KEY_REQUIRE_BIOMETRICS, biometricsSwitch.isChecked());
            biometricsSwitch.setTrackTintMode((biometricsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
            return;
        }

        Cipher cipherForPrompt = null;
        try {
            generateSecretKey();
            cipherForPrompt = getCipher();
        } catch (Exception e) {
            Log.e("AdvancedOptions", "Errore durante l'inizializzazione della chiave biometrica", e);
        }

        /// In this section of the code, we create the PopUp for log-in with Biometric Auth.
        /// The pop-up UI is different base by Manufacture Implementation.
        /// @FALLBACK
        /// If the Biometric Auth failed, don't apply the modifications to the DataManager.

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(AdvancedOptions.this, "Autenticazione non riuscita.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                try {
                    Cipher cipher = result.getCryptoObject() != null ? result.getCryptoObject().getCipher() : null;
                    if (cipher != null) {
                        byte[] payload = "biometric_toggle".getBytes("UTF-8");
                        byte[] encrypted = cipher.doFinal(payload);
                        if (encrypted != null && encrypted.length > 0) {
                            biometricsSwitch.setChecked(false);
                            DataManager.saveBoolData(AdvancedOptions.this, DataKeys.KEY_REQUIRE_BIOMETRICS, biometricsSwitch.isChecked());
                            biometricsSwitch.setTrackTintMode((biometricsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
                        }
                    } else {
                        Toast.makeText(AdvancedOptions.this, "Impossibile verificare l'autenticazione biometrica.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("AdvancedOptions", "Errore durante l'operazione crittografica biometrica", e);
                    Toast.makeText(AdvancedOptions.this, "Errore di sicurezza durante l'autenticazione.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        if(biometricsSwitch.isChecked()){
            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Modifica impostazioni")
                    .setSubtitle("Usa l'impronta o il viso per modificare questa impostazione.")
                    .setAllowedAuthenticators(authenticators)
                    .build();

            if (cipherForPrompt != null) {
                biometricPrompt.authenticate(new BiometricPrompt.CryptoObject(cipherForPrompt), promptInfo);
            } else {
                biometricPrompt.authenticate(promptInfo);
            }
        }
        else {
            biometricsSwitch.setChecked(true);
            DataManager.saveBoolData(AdvancedOptions.this, DataKeys.KEY_REQUIRE_BIOMETRICS, biometricsSwitch.isChecked());
            biometricsSwitch.setTrackTintMode((biometricsSwitch.isChecked()) ? PorterDuff.Mode.ADD : PorterDuff.Mode.MULTIPLY);
        }
    }
}