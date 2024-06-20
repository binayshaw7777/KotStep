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

- Multiple stepper styles:
  - Horizontal Numbered Stepper
  - Horizontal Tab Stepper
  - Horizontal Icon Stepper
  - Horizontal Dashed Stepper
  - Vertical Icon Stepper
  - Vertical Tab Stepper
  - Vertical Numbered Stepper
- Easy integration with Jetpack Compose
- Optional checkmark icons for completed steps.
- Highly customizable appearance and behavior

  
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

## <b>Horizontal Stepper - Tab</b>

```kotlin
HorizontalStepper(
    style = tabHorizontal(
        totalSteps = 3,
        currentStep = 1
    )
)
```

### `tabHorizontal` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`         	|
| `totalSteps`                     | The total number of steps.                           				                 | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/d2681946-d827-4c1d-ac6f-95b162e64009" width="280"/>

---

## <b>Horizontal Stepper - Icon</b>

```kotlin
HorizontalStepper(
    style = iconHorizontal(
	currentStep = 1,
        icons = listOf(
            Icons.Default.AccountCircle,
	    ...
            Icons.Default.DateRange
        )
    )
)
```

### `iconHorizontal` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`           |
| `icons`                 	   | A list of ImageVectors.                           				                	  | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/160962f6-7f5c-476c-b688-a4eded2712d9" width="280"/>

---

## <b>Horizontal Stepper - Numbered</b>

```kotlin
HorizontalStepper(
  style = numberedHorizontal(
    totalSteps = 5,
    currentStep = 1
  )
)      
```

### `numberedHorizontal` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`         	|
| `totalSteps`                     | The total number of steps.                           				                 | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/3b4d84e1-e2c6-4305-95dd-8cf1bc1dc1ee" width="280"/>

---

## <b>Horizontal Stepper - Dashed</b>

```kotlin
HorizontalStepper(
  style = dashed(
    totalSteps = 5,
    currentStep = 1
  )
)    
```

### `dashed` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`         	|
| `totalSteps`                     | The total number of steps.                           				                 | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/3b4d84e1-e2c6-4305-95dd-8cf1bc1dc1ee" width="280"/>

---

## <b>Vertical Stepper - Tab</b>

```kotlin
VerticalStepper(
  style = tabVertical(
    totalSteps = 5,
    currentStep = 1
  )
)    
```

### `tabVertical` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`         	|
| `totalSteps`                     | The total number of steps.                           				                 | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/3b4d84e1-e2c6-4305-95dd-8cf1bc1dc1ee" width="280"/>

---


## <b>Vertical Stepper - Icon</b>

```kotlin
VerticalStepper(
  style = iconVertical(
    currentStep = 1,
    icons = listOf(
      Icons.Default.AccountCircle,
      ...
      Icons.Default.DateRange
    )
  )
)    
```

### `iconVertical` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`           |
| `icons`                 	   | A list of ImageVectors.                           				                	  | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/3b4d84e1-e2c6-4305-95dd-8cf1bc1dc1ee" width="280"/>

---

## <b>Vertical Stepper - Numbered</b>

```kotlin
VerticalStepper(
  style = numberedVertical(
    totalSteps = 5,
    currentStep = 1
  )
)    
```

### `numberedVertical` Parameters

| Parameter                       | Description                                                                                          | Default Value       |
|----------------------------------|------------------------------------------------------------------------------------------------------|---------------------|
| `modifier`                       | The modifier for styling the Component. (Optional)                                                   | `Modifier`         	|
| `totalSteps`                     | The total number of steps.                           				                 | -                   |
| `currentStep`                    | The current step that is active.                                                                    | -                   |
| `stepStyle`                      | The style for the step numbers. (Optional)                                                          | `StepStyle()`       |


<b>Example:</b><br>
<img src="https://github.com/binayshaw7777/KotStep/assets/62587060/3b4d84e1-e2c6-4305-95dd-8cf1bc1dc1ee" width="280"/>

---

## `StepStyle` Parameters


|Property|    Data Type|    Default Value|    Description|
|----------------------------------|---------|------------------------------------------------------------------------------------------------------|---------------------|
|colors    |StepDefaults    |StepDefaults.defaultColors()|    Colors for the step indicator|
|stepSize|    Dp|    36.dp|    Size of the step indicator|
|stepShape|    Shape|    CircleShape|    Shape of the step indicator|
|textSize|    TextUnit    |16.sp|    Text size for the step indicator|
|iconSize    |Dp    |24.dp|    Icon size for the step indicator|
|lineThickness|    Dp|    6.dp|    Thickness of the line connecting steps|
|lineSize|    Dp|    20.dp|    Length of the line connecting steps|
|stepPadding|    Dp    |0.dp|    Padding around the step indicator|
|lineStyle|    LineStyle|    LineStyle.SOLID    |Style of the line connecting steps (SOLID, DASHED, DOTTED)|
|showCheckMarkOnDone|    Boolean|    true|    Whether to show a checkmark on completed steps|
|showStrokeOnCurrent|    Boolean|    true|    Whether to show a stroke around the current step|
|strokeCap|    StrokeCap|    StrokeCap.Square|    Style of the ends of the line connecting steps (ROUNDED, SQUARE)|
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
