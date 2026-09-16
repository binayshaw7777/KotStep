import com.android.build.api.dsl.Lint
import com.android.build.api.dsl.LintOptions
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.publish.maven.MavenPublication
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.vanniktech.maven.publish)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
                }
            }
        }
    }
    jvm("desktop") {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }
        }
    }
    wasmJs {
        browser()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "KotStep"
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.material3)
            implementation(libs.compose.material.icons.core)
            implementation(libs.kotlinx.collections.immutable)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        val desktopTest by getting {
            dependencies {
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.desktop.currentOs)
            }
        }
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.compose.constraintlayout)
        }
        androidInstrumentedTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTestJUnit4)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.test.ext.junit)
            implementation(libs.androidx.test.espresso.core)
        }
    }
}

android {
    namespace = "com.binayshaw7777.kotstep"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        targetSdk = 35

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
    lint {
        abortOnError = false
    }
}

// Debug-only tooling without source-set dependency gymnastics
dependencies {
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Slack Compose Lints (Android Lint checks)
    lintChecks(libs.compose.lint.checks)
}

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release"),
        )
    )
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    if (System.getenv("CI") != null || project.hasProperty("signingInMemoryKeyId")) {
        signAllPublications()
    }

    coordinates("io.github.binayshaw7777", "kotstep", "3.2.0")

    pom {
        name.set("KotStep")
        description.set("A customizable stepper component for Compose Multiplatform.")
        url.set("https://github.com/binayshaw7777/KotStep")
        inceptionYear.set("2024")

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
                url.set("https://github.com/binayshaw7777")
            }
        }

        scm {
            url.set("https://github.com/binayshaw7777/KotStep")
            connection.set("scm:git:https://github.com/binayshaw7777/KotStep.git")
            developerConnection.set("scm:git:ssh://git@github.com/binayshaw7777/KotStep.git")
        }
    }
}