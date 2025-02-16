// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.7")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}


plugins {
    id("com.android.application") version "8.8.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.10" apply false
    id("com.android.library") version "8.8.1" apply false
    id("com.google.devtools.ksp") version "2.1.10-1.0.29" apply false
}