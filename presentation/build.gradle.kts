plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures{
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.appcompat)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // orbit
    implementation(libs.orbit.core)
    implementation(libs.orbit.compose)
    implementation(libs.orbit.viewmodel)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // paging3
    implementation(libs.paging.compose)

    // refresh
    implementation(libs.androidx.material3)

    // 미정
    implementation("com.airbnb.android:lottie-compose:6.3.0")
    implementation("com.google.accompanist:accompanist-flowlayout:0.34.0") // 최신 버전 확인 필요

    implementation(libs.timber)
}