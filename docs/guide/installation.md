# Installation

[![](https://jitpack.io/v/binayshaw7777/KotStep.svg)](https://jitpack.io/#binayshaw7777/KotStep)

To get started with KotStep in your Android Jetpack Compose project, 
Add it in your root `build.gradle` at the end of repositories:

```
allprojects {
	repositories {
		...
	    maven { url 'https://jitpack.io' }
    }
}
```

Lastly, add the following dependency to your app's `build.gradle.kts` (Kotlin) or `build.gradle` (Groovy) file:

<details>
<summary>Kotlin</summary>
<br>

```kotlin
dependencies {
    implementation("com.github.binayshaw7777:KotStep:$currentVersion")
}
```
</details>

<details>
<summary>Groovy</summary>
<br>

```kotlin
dependencies {
    implementation 'com.github.binayshaw7777:KotStep:$currentVersion'
}
```
</details>