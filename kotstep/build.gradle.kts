import com.android.build.api.dsl.Lint
import com.android.build.api.dsl.LintOptions

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
}

android {
    namespace = "com.binayshaw7777.kotstep"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
    lint {
        abortOnError = false
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.compose.constraintlayout)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.kotlinx.collections.immutable)

    // Slack Compose Lints (Android Lint checks)
    lintChecks(libs.compose.lint.checks)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.binayshaw7777"
            artifactId = "KotStep"
            version = "2.3.1"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("KotStep")
                description.set("A customizable stepper component for Jetpack Compose.")
                url.set("https://github.com/binayshaw7777/KotStep")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("binayshaw7777")
                        name.set("Binay Shaw")
                        email.set("binayshaw7777@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/binayshaw7777/KotStep.git")
                    developerConnection.set("scm:git:ssh://[email protected]/binayshaw7777/KotStep.git")
                    url.set("https://github.com/binayshaw7777/KotStep")
                }
            }
        }
    }
}