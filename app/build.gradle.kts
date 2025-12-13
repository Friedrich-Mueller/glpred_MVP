plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.glpred"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.glpred"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
//repositories {
//    google()
//    mavenCentral()
//}
dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // --- Networking ---
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // --- JSON parsing (Moshi) ---
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)

    // (Optional) Kotlin‑reflect if you later switch to Moshi‑Kotlin:
    // implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    // FreeStyle Libre 3 dependencies
    // Networking
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okio:okio:3.7.0")
    // JSON parsing
    implementation("org.json:json:20231013")
    // UI helpers
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

}