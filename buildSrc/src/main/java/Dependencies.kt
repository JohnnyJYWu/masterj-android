object Versions {
    const val COMPILE_SDK = 33
    const val TARGET_SDK = 33
    const val MIN_SDK = 23
    const val BUILD_TOOLS_VERSION = "33.0.0"

    const val KOTLIN = "1.8.10"
    const val KOTLIN_COROUTINES = "1.6.0"

    // AndroidX
    const val ANDROIDX_CORE_KTX_VERSION = "1.10.1"
    const val ANDROIDX_APPCOMPAT_VERSION = "1.6.1"
    const val ANDROIDX_FRAGMENT_KTX_VERSION = "1.5.5"
    const val ANDROIDX_ACTIVITY_KTX_VERSION = "1.6.1"
    const val ANDROIDX_ROOM_VERSION = "2.5.0"
    const val ANDROIDX_LIFECYCLE_RUNTIME_VERSION = "2.6.1"
    const val ANDROIDX_DATA_BINDING_VERSION = "4.0.1"

    const val MATERIAL_VERSION = "1.9.0"
    const val ANDROID_RECYCLERVIEW_VERSION = "1.2.1"
    const val ANDROID_CONSTRAINT_LAYOUT_VERSION = "2.1.4"

    // Compose
    const val COMPOSE_ACTIVITY_VERSION = "1.7.2"
    const val COMPOSE_BOM_VERSION = "2023.06.01"
    const val COMPOSE_LIFECYCLE_VIEWMODEL_VERSION = "2.6.1"

    // Json
    const val GSON_VERSION = "2.10.1"
    const val MOSHI_VERSION = "1.14.0"
}

object Libs {

    // Kotlin
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN}"
    const val KOTLIN_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android"
    const val KOTLIN_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core"

    // AndroidX
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT_VERSION}"
    const val ANDROIDX_FRAGMENT_KTX =
        "androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT_KTX_VERSION}"
    const val ANDROIDX_ACTIVITY_KTX =
        "androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY_KTX_VERSION}"
    const val ANDROIDX_ROOM = "androidx.room:room-ktx:${Versions.ANDROIDX_ROOM_VERSION}"
    const val ANDROIDX_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_RUNTIME_VERSION}"
    const val ANDROIDX_DATA_BINDING = "androidx.databinding:databinding-runtime:${Versions.ANDROIDX_DATA_BINDING_VERSION}"

    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL_VERSION}"
    const val ANDROID_RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.ANDROID_RECYCLERVIEW_VERSION}"
    const val ANDROID_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.ANDROID_CONSTRAINT_LAYOUT_VERSION}"

    // Compose
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY_VERSION}"
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM_VERSION}"
    const val COMPOSE_UI = "androidx.compose.ui:ui"
    const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
    const val COMPOSE_MATERIAL3 = "androidx.compose.material3:material3"
    const val COMPOSE_LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.COMPOSE_LIFECYCLE_VIEWMODEL_VERSION}"

    // Json
    const val GSON = "com.google.code.gson:gson:${Versions.GSON_VERSION}"
    const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}"
}

object Modules {
    const val BASE = ":base"
}
