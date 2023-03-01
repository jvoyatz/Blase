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
    implementation(project(":core:network:v1"))
    implementation(project(":data:activities:net-source"))
    //implementation(project(":data:activities:db-source"))
    implementation(project(":core:domain"))
    implementation(project(":core:logging"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)
}