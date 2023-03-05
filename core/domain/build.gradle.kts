plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies{
    implementation(project(":core:common"))
    //implementation(libs.timber)
    implementation(libs.javax.inject)
    implementation(libs.coroutines)
}