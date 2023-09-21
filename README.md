<h1 align="center">KotStep</h1>

<p align="center">
 <img alt="material" src="https://custom-icon-badges.demolab.com/badge/material%20you-palegreen?style=for-the-badge&logoColor=black&logo=material-you"/></a>
  <img alt="API" src="https://img.shields.io/badge/Api%2021+-50f270?logo=android&logoColor=black&style=for-the-badge"/></a>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/></a>
  <img alt="Jetpack Compose" src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label="/></a> 
  <a href="https://hits.sh/github.com/binayshaw7777/KotStep/"><img alt="Hits" src="https://hits.sh/github.com/binayshaw7777/KotStep.svg?style=for-the-badge&label=Views&extraCount=10&color=54856b"/></a>
  <a href="https://github.com/binayshaw7777/KotStep/releases/latest"><img src="https://img.shields.io/github/v/release/binayshaw7777/KotStep?color=purple&include_prereleases&logo=github&style=for-the-badge"/>
</p>

<p align="center">💜 KotStep is a Jetpack Compose library that simplifies the creation of customizable step-by-step UI components in your Android applications. It allows you to easily integrate vertical and horizontal stepper components with icons, titles, and various customization options.
<br>
<br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/2cf2c41a-6812-484a-bcdc-d5f72cad94f0"/>
</p>

## Features

- Vertical and horizontal stepper components.
- Customizable colors, icons, and labels for each step.
- Support for both text labels and icon-based steps.
- Optional checkmark icons for completed steps.
- Easily integrate step-by-step user interfaces into your Jetpack Compose apps.

## Installation

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

# Usage

- <b>Horizontal Sequenced Stepper</b>

```kotlin
HorizontalSequencedStepper(
    totalSteps = 5,
    currentStep = 1
)
```

### `HorizontalSequencedStepper` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the composable. (Optional)                                                | `Modifier`          |
| `totalSteps`                     | The total number of steps in the horizontal sequenced stepper.                                      | -                   |
| `currentStep`                    | The current step that is active.                                                                    | `1`                 |
| `lineThickness`                  | The thickness of the connecting line between steps.                                                 | `1.dp`              |
| `stepSize`                       | The size of each step in the stepper.                                                               | `28.dp`             |
| `stepShape`                      | The shape of each step in the stepper.                                                              | `CircleShape`       |
| `completedColor`                 | The color for completed steps.                                                                      | `Color.Blue`        |
| `incompleteColor`                | The color for incomplete steps.                                                                     | `Color.Gray`        |
| `checkMarkColor`                 | The color of the checkmark symbol for completed steps.                                              | `Color.White`       |
| `stepTitleOnIncompleteColor`     | The color of step titles on incomplete steps.                                                        | `checkMarkColor`    |
| `stepTitleOnCompleteColor`       | The color of step titles on completed steps.                                                        | `completedColor`    |
| `stepNameOnIncompleteColor`      | The color of step names on incomplete steps.                                                         | `checkMarkColor`    |
| `stepNameOnCompleteColor`        | The color of step names on completed steps.                                                         | `completedColor`    |
| `stepDescription`                | A list of step descriptions. The length should match `totalSteps`.                                   | `List(totalSteps) { "" }` |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/d2681946-d827-4c1d-ac6f-95b162e64009" width="280"/>

---

- <b>Horizontal Icons Stepper</b>

```kotlin
HorizontalIconStepper(
    totalSteps = 5,
    currentStep = 1,
    stepIconsList = listOf(
        Icons.Default.AccountBox,
        Icons.Default.AddCircle,
        Icons.Default.Build,
        Icons.Default.Face,
        Icons.Default.Home
    )
)
```

### `HorizontalIconStepper` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the composable. (Optional)                                                | `Modifier`          |
| `totalSteps`                     | The total number of steps in the horizontal icon stepper.                                           | -                   |
| `currentStep`                    | The current step that is active.                                                                    | `1`                 |
| `lineThickness`                  | The thickness of the connecting line between steps.                                                 | `1.dp`              |
| `stepSize`                       | The size of each step in the stepper.                                                               | `28.dp`             |
| `stepShape`                      | The shape of each step in the stepper.                                                              | `CircleShape`       |
| `completedColor`                 | The color for completed steps.                                                                      | `Color.Blue`        |
| `incompleteColor`                | The color for incomplete steps.                                                                     | `Color.Gray`        |
| `checkMarkColor`                 | The color of the checkmark symbol for completed steps.                                              | `Color.White`       |
| `stepTitleOnIncompleteColor`     | The color of step titles on incomplete steps.                                                        | `checkMarkColor`    |
| `stepTitleOnCompleteColor`       | The color of step titles on completed steps.                                                        | `completedColor`    |
| `stepDescription`                | A list of step descriptions. The length should match `totalSteps`.                                   | `List(totalSteps) { "" }` |
| `stepIconsList`                  | A list of ImageVectors representing icons for each step.                                             | -                   |
| `stepIconsColorOnIncomplete`     | The color of step icons on incomplete steps.                                                         | `checkMarkColor`    |
| `stepIconsColorOnComplete`       | The color of step icons on completed steps.                                                         | `incompleteColor`   |
| `showCheckMarkOnDone`           | Whether to display a checkmark icon for completed steps.                                             | `false`             |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/160962f6-7f5c-476c-b688-a4eded2712d9" width="280"/>

---

- <b>Vertical Sequenced Stepper</b>

```kotlin
VerticalSequencedStepper(
    totalSteps = 5,
    currentStep = 1
)       
```

### `VerticalSequencedStepper` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the composable. (Optional)                                                | `Modifier`          |
| `totalSteps`                     | The total number of steps in the vertical sequenced stepper.                                        | -                   |
| `currentStep`                    | The current step that is active.                                                                    | `0`                 |
| `lineThickness`                  | The thickness of the connecting line between steps.                                                 | `1.dp`              |
| `stepSize`                       | The size of each step in the stepper.                                                               | `28.dp`             |
| `stepShape`                      | The shape of each step in the stepper.                                                              | `CircleShape`       |
| `completedColor`                 | The color for completed steps.                                                                      | `Color.Blue`        |
| `incompleteColor`                | The color for incomplete steps.                                                                     | `Color.Gray`        |
| `checkMarkColor`                 | The color of the checkmark symbol for completed steps.                                              | `Color.White`       |
| `stepTitleOnIncompleteColor`     | The color of step titles on incomplete steps.                                                        | `checkMarkColor`    |
| `stepTitleOnCompleteColor`       | The color of step titles on completed steps.                                                        | `completedColor`    |
| `stepNameOnIncompleteColor`      | The color of step names on incomplete steps.                                                         | `checkMarkColor`    |
| `stepNameOnCompleteColor`        | The color of step names on completed steps.                                                         | `completedColor`    |
| `stepDescription`                | A list of step descriptions. The length should match `totalSteps`.                                   | `List(totalSteps) { "" }` |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/3b4d84e1-e2c6-4305-95dd-8cf1bc1dc1ee" width="280"/>

---

- <b>Vertical Icon Stepper</b>

```kotlin
VerticalIconStepper(
    totalSteps = 5,
    currentStep = 1,
    stepIconsList = listOf(
        Icons.Default.AccountBox,
        Icons.Default.AddCircle,
        Icons.Default.Build,
        Icons.Default.Face,
        Icons.Default.Home
    )
)
```

### `VerticalIconStepper` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the composable. (Optional)                                                | `Modifier`          |
| `totalSteps`                     | The total number of steps in the vertical icon stepper.                                             | -                   |
| `currentStep`                    | The current step that is active.                                                                    | `0`                 |
| `lineThickness`                  | The thickness of the connecting line between steps.                                                 | `1.dp`              |
| `stepSize`                       | The size of each step in the stepper.                                                               | `28.dp`             |
| `stepShape`                      | The shape of each step in the stepper.                                                              | `CircleShape`       |
| `completedColor`                 | The color for completed steps.                                                                      | `Color.Blue`        |
| `incompleteColor`                | The color for incomplete steps.                                                                     | `Color.Gray`        |
| `checkMarkColor`                 | The color of the checkmark symbol for completed steps.                                              | `Color.White`       |
| `stepTitleOnIncompleteColor`     | The color of step titles on incomplete steps.                                                        | `checkMarkColor`    |
| `stepTitleOnCompleteColor`       | The color of step titles on completed steps.                                                        | `completedColor`    |
| `stepIconsList`                  | A list of ImageVectors representing icons for each step.                                             | -                   |
| `stepIconsColorOnIncomplete`     | The color of step icons on incomplete steps.                                                         | `checkMarkColor`    |
| `stepIconsColorOnComplete`       | The color of step icons on completed steps.                                                         | `incompleteColor`   |
| `stepDescription`                | A list of step descriptions. The length should match `totalSteps`.                                   | `List(totalSteps) { "" }` |
| `showCheckMarkOnDone`           | Whether to display a checkmark icon for completed steps.                                             | `false`             |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/63ba0fab-038d-4b8d-83a2-44554a783aa9" width="280"/>

---

## Reporting Issues and Requesting Features✨
If you encounter any issues or have feature requests, please create a new [issue](https://github.com/binayshaw7777/KotStep/issues) in this repository.

## Supporting KotStep :heart:
Support it by joining __[stargazers](https://github.com/binayshaw7777/KotStep/stargazers)__ for this repository. :star: <br>
Also __[follow](https://github.com/binayshaw7777)__ me for my next creations! 🤩

<br>

[![Star History Chart](https://api.star-history.com/svg?repos=binayshaw7777/KotStep&type=Date)](https://star-history.com/#binayshaw7777/KotStep&Date)

<br>
---

```
Copyright 2023 binayshaw7777

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
