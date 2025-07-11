// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.com.google.firebase.crashlytics) apply false
}
true // Needed to make the Suppress annotation work for