// not type safe
val libs: VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id(libs.plugins.android.library)
}

dependencies {
    implementation(libs.androidx.core.ktx)
}