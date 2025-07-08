plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // serialization
    implementation(libs.kotlinx.serialization.json)

    // paging3 - common(안드로이드 의존성 x)
    implementation(libs.paging.common)

    // Hilt
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}