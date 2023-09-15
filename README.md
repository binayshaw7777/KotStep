<h1 align="center">

KotStep Library

</h1>


<p align="center">
 <img alt="material" src="https://custom-icon-badges.demolab.com/badge/material%20you-palegreen?style=for-the-badge&logoColor=black&logo=material-you"/></a>
  <img alt="API" src="https://img.shields.io/badge/Api%2021+-50f270?logo=android&logoColor=black&style=for-the-badge"/></a>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/></a>
  <img alt="Jetpack Compose" src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label="/></a> 
  <a href="https://hits.sh/github.com/binayshaw7777/KotStep/"><img alt="Hits" src="https://hits.sh/github.com/binayshaw7777/KotStep.svg?style=for-the-badge&label=Views&extraCount=10&color=54856b"/></a>
</p>

<p align="center">💜 KotStep is a Jetpack Compose library that simplifies the creation of customizable step-by-step UI components in your Android applications. It allows you to easily integrate vertical and horizontal stepper components with icons, titles, and various customization options.</p>

## Features

- Vertical and horizontal stepper components.
- Customizable colors, icons, and labels for each step.
- Support for both text labels and icon-based steps.
- Optional checkmark icons for completed steps.
- Easily integrate step-by-step user interfaces into your Jetpack Compose apps.

## Installation

To get started with KotStep in your Android Jetpack Compose project, 
- Add it in your root `build.gradle` at the end of repositories:

```
allprojects {
	repositories {
		...
	    maven { url 'https://jitpack.io' }
    }
}
```

- Lastly, add the following dependency to your app's `build.gradle.kts` (Kotlin) or `build.gradle` (Groovy) file:

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

# Usage

- <b>Horizontal Sequenced Stepper</b>

```kotlin
HorizontalSequencedStepper(
    totalSteps = totalSteps,
    currentStep = currentStep,
    stepSize = stepItemSize.dp,
    lineThickness = lineThickness.dp
)
```

- <b>Horizontal Icons Stepper</b>

```kotlin
HorizontalIconStepper(
    totalSteps = totalSteps,
    currentStep = currentStep,
    stepSize = stepItemSize.dp,
    lineThickness = lineThickness.dp,
    stepIconsList = listOf(
        Icons.Default.AccountBox,
        Icons.Default.AddCircle,
        Icons.Default.Build,
        Icons.Default.Face,
        Icons.Default.Home
    )
)
```


- <b>Vertical Sequenced Stepper</b>

```kotlin
VerticalSequencedStepper(
    totalSteps = 5,
    currentStep = 1,
    stepSize = 35.dp,
    lineThickness = 3.dp
)       
```

- <b>Vertical Icon Stepper</b>

```kotlin
VerticalIconStepper(
    totalSteps = 5,
    currentStep = 1,
    stepSize = 35.dp,
    lineThickness = 3.dp,
    stepIconsList = listOf(
        Icons.Default.AccountBox,
        Icons.Default.AddCircle,
        Icons.Default.Build,
        Icons.Default.Face,
        Icons.Default.Home
    )
)
```

## Reporting Issues and Requesting Features✨
If you encounter any issues or have feature requests, please create a new [issue](https://github.com/binayshaw7777/KotStep/issues) in this repository.

## Supporting ComposeCards :heart:
Support it by joining __[stargazers](https://github.com/binayshaw7777/KotStep/stargazers)__ for this repository. :star: <br>
Also __[follow](https://github.com/binayshaw7777)__ me for my next creations! 🤩
