import org.gradle.kotlin.dsl.implementation
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.tasktrek"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tasktrek"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties =  Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField ("String", "GOOGLE_SERVER_CLIENT_ID", "\"${properties.getProperty("GOOGLE_SERVER_CLIENT_ID")}\"")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.adapter.rxjava2)
    implementation (libs.converter.gson)

    // okhttp
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    // Koin for di
    implementation (libs.koin.androidx.compose)
    implementation (libs.koin.androidx.compose.navigation)

    // Coroutines for asynchronous calls (and Deferred adapter)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    // lifecycle
    implementation (libs.androidx.lifecycle.extensions)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx.v220)

    //recyclerview and cardview
    implementation (libs.androidx.recyclerview)
    implementation (libs.androidx.cardview)

    //Google OAuth
    implementation (libs.androidx.credentials)
    implementation (libs.androidx.credentials.play.services.auth)
    implementation (libs.googleid)

    // Navigation compose
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // UI
    implementation(libs.accompanist.systemuicontroller)
    implementation (libs.androidx.material.icons.extended)
}