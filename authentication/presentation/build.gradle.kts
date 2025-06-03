plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    // TODO unify the jvmToolchain version across all modules
    jvmToolchain(11)
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.authentication.domain)
                implementation(projects.operation)

                implementation(projects.coroutines)

                implementation(libs.aungthiha.snackbar.channel)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
//                implementation(libs.koin.composeViewModel)

                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.navigation)

                implementation(libs.ktor.serialization.kotlinx.json)

                implementation(projects.compose)

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
            }
        }
    }
}
