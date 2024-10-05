plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.foodtogo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodtogo"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



    implementation ("com.google.code.gson:gson:2.11.0")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

//Room
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    implementation ("com.airbnb.android:lottie:6.1.0")


    // Jetpack Compose Integration
    implementation ("androidx.navigation:navigation-compose:2.8.1")

    // Views/Fragments Integration
    implementation ("androidx.navigation:navigation-fragment:2.8.1")
    implementation ("androidx.navigation:navigation-ui:2.8.1")

    // Feature module support for Fragments
    implementation ("androidx.navigation:navigation-dynamic-features-fragment:2.8.1")

    // Testing Navigation
    androidTestImplementation ("androidx.navigation:navigation-testing:2.8.1")

    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("com.google.android.material:material:1.9.0")

}