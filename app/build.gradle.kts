import java.util.Properties
import java.io.File

plugins {
    alias(libs.plugins.android.application)
}

//DOT ENV INTEGRATION
val envProperties = Properties()
val envFile = File(rootProject.projectDir, ".env")
if (envFile.exists())
    envFile.inputStream().use { envProperties.load(it) }
val apiKey = envProperties.getProperty("API_KEYS", "")
val apiSupabaseURL = envProperties.getProperty("API_SUPABASE_URL", "")
val apiSupabaseANON = envProperties.getProperty("API_SUPABASE_ANON", "")

android {
    namespace = "com.andreafilice.lavorami"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.andreafilice.lavorami"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
        var buildNumber = "07022026"

        resValue("string", "app_version", versionName ?: "1.0.0")
        resValue("string", "appVersionFull", ("$versionName ($buildNumber)"))

        manifestPlaceholders["API_KEY"] = apiKey
        manifestPlaceholders["API_SUPABASE_URL"] = apiSupabaseURL
        manifestPlaceholders["API_SUPABASE_ANON"] = apiSupabaseANON
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.play.services.maps)
    implementation(libs.dotenv.java)
    implementation(libs.androidx.browser)
    implementation(libs.shimmer)
}