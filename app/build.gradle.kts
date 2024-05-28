buildscript {
    repositories {
        google()
    }
}


plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

android {
    namespace = "com.example.parkingdev01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.parkingdev01"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "35.0.0 rc3"
}

dependencies {
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.6.6")

    implementation("com.google.dagger:hilt-android:2.49")
    implementation(libs.play.services.maps)
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // OpenCSV
    implementation("com.opencsv:opencsv:5.5.2")

    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.compose.material:material-icons-extended:compose_version")
    implementation("com.google.accompanist:accompanist-flowlayout:0.17.0")
    implementation("androidx.paging:paging-compose:3.2.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.2-alpha")
    val compose_version = "1.5.0"

    // Compose Nav Destinations
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("io.github.raamcosta.compose-destinations:core:1.1.2-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.1.2-beta")
    implementation ("androidx.compose.material:material-icons-extended:$compose_version")
    implementation ("com.google.maps.android:maps-compose:1.0.0")
    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.40.5")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    // QR Code
    implementation("com.lightspark:compose-qr-code:1.0.1")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.6.1")

    implementation ("com.squareup.moshi:moshi-kotlin:1.12.0")
// image
    implementation("com.google.accompanist:accompanist-coil:0.15.0")

    // -- Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    // Google Maps
    implementation ("com.google.android.gms:play-services-maps:18.0.2")
    implementation ("com.google.maps.android:maps-compose:1.0.0")

}
