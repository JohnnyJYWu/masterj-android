plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.masterj.base"
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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

    // AndroidX
    implementation(Libs.ANDROIDX_CORE_KTX)
    implementation(Libs.ANDROIDX_APPCOMPAT)
    implementation(Libs.ANDROIDX_FRAGMENT_KTX)
    implementation(Libs.ANDROIDX_ACTIVITY_KTX)
    implementation(Libs.ANDROIDX_LIFECYCLE_RUNTIME)
    implementation(Libs.ANDROIDX_DATA_BINDING)

    // Material
    implementation(Libs.MATERIAL)
    implementation(Libs.ANDROID_RECYCLERVIEW)
    implementation(Libs.ANDROID_CONSTRAINT_LAYOUT)

    // Compose
    implementation(Libs.COMPOSE_ACTIVITY)
    implementation(platform(Libs.COMPOSE_BOM))
    implementation(Libs.COMPOSE_UI)
    implementation(Libs.COMPOSE_UI_GRAPHICS)
    implementation(Libs.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Libs.COMPOSE_MATERIAL3)

    // Json
    implementation(Libs.GSON)
    implementation(Libs.MOSHI)
}
