// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("org.jetbrains.dokka") version "1.9.20"
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootDir.resolve("docs"))
}