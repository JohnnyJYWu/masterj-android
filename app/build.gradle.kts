plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

apply(from = "auto-version.gradle")
// apply(from = "../auto-lib-version.gradle")

android {
    namespace = "com.masterj.demo"
    compileSdk = Versions.COMPILE_SDK
    buildToolsVersion = Versions.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = "com.masterj.demo"
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

        ndk { // not necessary
            abiFilters.apply {
                add("armeabi-v7a")
                add("arm64-v8a")
//                add("x86")
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    ndkVersion = "23.1.7779620" // not necessary
}

dependencies {

    implementation(project(Modules.BASE))

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
    implementation(files("../unityLibrary/libs/unity-classes.jar"))
    implementation(project(":unityLibrary"))
}
