# Usage

## Horizontal Sequenced Stepper

```kotlin
HorizontalSequencedStepper(
    totalSteps = 5,
    currentStep = 1
)
```
<b>`HorizontalSequencedStepper` Parameters</b>

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

## Horizontal Icons Stepper

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

<b>`HorizontalIconStepper` Parameters</b>

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

## Vertical Sequenced Stepper

```kotlin
VerticalSequencedStepper(
    totalSteps = 5,
    currentStep = 1
)       
```

<b>`VerticalSequencedStepper` Parameters</b>

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

## Vertical Icon Stepper

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

<b>`VerticalIconStepper` Parameters</b>

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