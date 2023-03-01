plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "${libs.versions.packageName.get()}.data.activities.repo.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core:logging"))
    implementation(project(":core:network:v1"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)

    //testing
   // implementation(libs.okhttp.mockserver)
}