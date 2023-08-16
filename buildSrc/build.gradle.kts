plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.8.10"
    kotlin("kapt") version "1.8.10"
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

repositories {
    gradlePluginPortal()
    mavenLocal()
    mavenCentral()
    google()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    kapt("com.google.auto.service:auto-service:1.0")
}
