# Architecture Decision Record: KotStep V2 Legacy API Deprecation Policy

- **Date:** 2026-09-14
- **Status:** Accepted
- **Driver:** KotStep Compose Multiplatform Migration (T-10.1)
- **Supersedes / Extends:** [0001-v2-legacy-strategy.md](file:///Users/binay/AndroidStudioProjects/KotStep/docs/decisions/0001-v2-legacy-strategy.md)

---

## 1. Context and Problem Statement

KotStep historically shipped two distinct stepper APIs:
1. **V2 Legacy API**: Sealed-class component model (`HorizontalStepper`, `VerticalStepper`, `StepperStyle`, `tabHorizontal`, `numberedHorizontal`, `iconHorizontal`, etc.) spanning 26+ files.
2. **V3 Modern API**: Declarative type-safe DSL ([`KotStep`](file:///Users/binay/AndroidStudioProjects/KotStep/kotstep/src/commonMain/kotlin/com/binayshaw7777/kotstep/v3/KotStep.kt), [`KotStepScope`](file:///Users/binay/AndroidStudioProjects/KotStep/kotstep/src/commonMain/kotlin/com/binayshaw7777/kotstep/v3/model/KotStepScope.kt), `step()`, [`StepStyle`](file:///Users/binay/AndroidStudioProjects/KotStep/kotstep/src/commonMain/kotlin/com/binayshaw7777/kotstep/v3/model/style/StepStyle.kt)).

The V2 implementation couples strongly to Android-specific APIs:
- `androidx.constraintlayout:constraintlayout-compose` for indicator and connector placement.
- `LocalContext` for resource lookups.

Porting ConstraintLayout V2 to Compose Multiplatform (`commonMain`) introduces excessive complexity, increases bundle size, and carries high maintenance cost for an obsolete API surface. Conversely, immediately dropping V2 would cause breaking changes for established Android consumers upgrading to `3.2.0`.

---

## 2. Decision

1. **Retain V2 exclusively in `androidMain`**:
   - V2 source files remain in `kotstep/src/androidMain` unchanged.
   - V2 is completely absent from `commonMain`, `desktopMain`, `wasmJsMain`, and `iosMain`.
   - Android artifacts continue to export V2 symbols, guaranteeing binary and source compatibility for Android consumers.

2. **Add compiler deprecation annotations (`DeprecationLevel.WARNING`) in v3.2.0**:
   - All public V2 entrypoints will be annotated with `@Deprecated`.
   - Provide actionable replacement guidance pointing to the V3 DSL.

3. **Multi-release phase-out timeline**:
   - V2 remains functional through the entire 3.x lifecycle.
   - V2 is scheduled for full removal in **v4.0.0**, alongside the removal of `androidx.constraintlayout:constraintlayout-compose`.

---

## 3. Deprecation Timeline

| Release | Deprecation Level | Status & Behavior |
|---|---|---|
| **v3.2.0** (CMP Launch) | `DeprecationLevel.WARNING` | V2 operational in `androidMain`. Compiler emits warning with migration hint. Release notes formally announce deprecation. |
| **v3.3.0 – v3.4.0** | `DeprecationLevel.WARNING` | Maintenance mode. Critical bug fixes only. No new V2 feature work. |
| **v3.5.0** (Pre-v4) | `DeprecationLevel.ERROR` | Compilation fails unless suppressed via `@Suppress("DEPRECATION")`. Final warning before removal. |
| **v4.0.0** (Major Release) | **REMOVED** | V2 code deleted from `androidMain`. `constraintlayout-compose` dependency removed entirely. |

---

## 4. Deprecation Annotation Template

```kotlin
@Deprecated(
    message = "KotStep V2 is deprecated and Android-only. Migrate to the cross-platform KotStep V3 DSL.",
    replaceWith = ReplaceWith(
        expression = "KotStep(currentStep = { currentStep.toFloat() }, style = KotStepStyle(...)) { ... }",
        imports = ["com.binayshaw7777.kotstep.v3.KotStep", "com.binayshaw7777.kotstep.v3.model.style.KotStepStyle"]
    ),
    level = DeprecationLevel.WARNING
)
```

Targeted symbols in `kotstep/src/androidMain`:
- `HorizontalStepper`
- `VerticalStepper`
- `StepperStyle`
- Helper factory methods (`tabHorizontal`, `numberedHorizontal`, `iconHorizontal`, `dashedHorizontal`, `numberedVertical`, `iconVertical`, `tabVertical`)

---

## 5. Migration Guide: V2 Sealed Classes → V3 DSL

### Example A: Horizontal Numbered Stepper

**Legacy V2:**
```kotlin
HorizontalStepper(
    style = numberedHorizontal(
        totalSteps = 4,
        currentStep = currentStep,
        stepStyle = StepStyle(...)
    )
)
```

**Modern V3 DSL:**
```kotlin
KotStep(
    currentStep = { currentStep.toFloat() },
    style = KotStepStyle(
        stepLayoutStyle = StepLayoutStyle.Horizontal,
        stepStyle = StepStyle(...)
    )
) {
    step(title = "Account")
    step(title = "Profile")
    step(title = "Billing")
    step(title = "Confirm")
}
```

### Example B: Vertical Stepper with Labels

**Legacy V2:**
```kotlin
VerticalStepper(
    style = numberedVerticalWithLabel(
        totalSteps = 3,
        currentStep = currentStep,
        trailingLabels = listOf(
            { Text("Step 1") },
            { Text("Step 2") },
            { Text("Step 3") }
        )
    )
)
```

**Modern V3 DSL:**
```kotlin
KotStep(
    currentStep = { currentStep.toFloat() },
    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Vertical)
) {
    step(
        title = "Order Placed",
        trailingContent = { Text("Details for step 1") }
    )
    step(
        title = "Processing",
        trailingContent = { Text("Details for step 2") }
    )
    step(
        title = "Delivered",
        trailingContent = { Text("Details for step 3") }
    )
}
```

---

## 6. Consequences

### Positive
- **Zero breakage for Android consumers**: Existing applications upgrade without compile failures.
- **Clean multiplatform core**: `commonMain` remains 100% free from Android and ConstraintLayout dependencies.
- **Clear migration path**: Developers receive IDE warnings with code guidance.
- **Future optimization**: Dropping `constraintlayout-compose` in v4.0.0 will reduce the library's Android transitive footprint.

### Negative / Trade-offs
- Android AAR still packages V2 code and `constraintlayout-compose` dependency throughout the 3.x series.
- Documentation must maintain dual notes during the transition window.
