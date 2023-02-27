// not type safe
val libs: VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id(libs.plugins.android.library)
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
//apply plugin: ‘com.android.library’
//apply plugin: ‘kotlin-android’android {
// compileSdkVersion Versions.compile_sdkdefaultConfig {
// minSdkVersion Versions.min_sdk
// targetSdkVersion Versions.target_sdk
// versionCode 1
// versionName “1.0”testInstrumentationRunner
// “android.support.test.runner.AndroidJUnitRunner”
// }
//}