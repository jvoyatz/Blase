import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "${libs.versions.packageName.get()}.data.activities.repo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        buildConfigField("String", "BORED_API_URL", "\"https://www.boredapi.com/api/\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    //modules
    implementation(project(":core:common"))
    //implementation(project(":core:logging"))
    implementation(project(":core:domain"))
    implementation(project(":core:network:v1"))
    implementation(project(":core:database"))
    implementation(libs.timber)
    //dagger-hilt
    implementation(libs.androidx.core.ktx)
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    //room
    implementation(libs.room)
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.proc)
    kapt(libs.room.kapt)
}