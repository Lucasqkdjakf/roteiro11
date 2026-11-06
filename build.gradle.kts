plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" // Verifique a versão compatível com seu Kotlin
}

dependencies {
    // Dependências padrão do Compose omitidas para brevidade...

    // Room Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    // ViewModel Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
}