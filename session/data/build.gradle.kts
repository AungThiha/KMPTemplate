import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
}

kotlin {

    jvm()

    androidLibrary {
        namespace = "aung.thiha.session"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {

        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
        commonMain.dependencies {
            implementation(projects.storage)
            implementation(projects.session.domain)

            implementation(libs.androidx.datastore)
            implementation(libs.koin.core)
            implementation(libs.kermit)
        }
    }
}
