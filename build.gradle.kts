// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.android.library") version "8.1.0" apply false
}

ext {
    set("kotlin_version", Versions.KOTLIN)
    set("compileSdkVersion", Versions.COMPILE_SDK)
    set("buildToolsVersion", Versions.BUILD_TOOLS_VERSION)
    set("minSdkVersion", Versions.MIN_SDK)
    set("targetSdkVersion", Versions.TARGET_SDK)
}
