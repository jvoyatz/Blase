@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = libs.versions.packageName.get() + "core.network.v1"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
}

dependencies {
    //implementation(project(":core:logging"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // okhttp - bom
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    implementation(libs.timber)
    implementation(project(":core:testing"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("com.google.truth:truth:1.1.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
}