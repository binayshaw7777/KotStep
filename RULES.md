# KotStep Project Rules

These rules apply to all development, refactoring, and agent workflows within the KotStep repository.

---

## 1. Multiplatform Purity (`commonMain`)

1. **Strictly Android-Free**:
   - `kotstep/src/commonMain` and `kotstep-sdui/src/commonMain` must NEVER import `android.*`, `java.*`, `R.*`, `LocalContext`, `Toast`, or platform-specific APIs.
   - Use Compose Multiplatform abstractions (`androidx.compose.ui.*`, `androidx.compose.runtime.*`, `androidx.compose.foundation.*`).
2. **Audit Requirement**:
   - Any PR or commit touching `commonMain` must pass:
     ```bash
     grep -rn "android\." kotstep/src/commonMain kotstep-sdui/src/commonMain
     ```
     (Must output 0 matches).

---

## 2. Public API Stability

1. **V3 DSL Protection**:
   - `KotStep()`, `KotStepScope`, `StepItem`, `KotStepStyle`, and related public configuration data classes are published library APIs.
   - Never remove, rename, or break signatures of published public methods.
   - Deprecations must use `@Deprecated(message, replaceWith, level = DeprecationLevel.WARNING)`.
2. **V2 Legacy Isolation**:
   - Legacy V2 components remain isolated in `kotstep/src/androidMain` due to `constraintlayout-compose` dependency.
   - Do NOT port V2 legacy to `commonMain`.

---

## 3. Layout Constraint & Rendering Safety

1. **No Unconstrained Crashes**:
   - Custom `Layout` composables must explicitly clamp min/max constraints.
   - When measuring text or slot content, always clamp using:
     ```kotlin
     minWidth = calculatedWidth.coerceAtMost(constraints.maxWidth)
     minHeight = calculatedHeight.coerceAtMost(constraints.maxHeight)
     ```
2. **Dynamic Slot Separation**:
   - Step indicator slot (circle/badge/number) and step label slots (title, subtitle, error, trailing/leading content) must remain structurally separate to prevent layout collisions.

---

## 4. Module Boundaries & Responsibilities

- `:kotstep`: Pure, high-performance Compose Multiplatform stepper primitives. Zero heavy external dependencies (only Compose runtime/ui/foundation).
- `:kotstep-sdui`: Server-Driven UI module. Depends on `:kotstep` and `kotlinx-serialization-json`. Provides JSON parsing, reactive state management, mutations, and pluggable resolvers.
- `:app`, `:desktopApp`, `:webApp`, `:iosApp`: Showcase and demo applications. No library code belongs here.

---

## 5. Testing & Verification

1. **Mandatory Build Checks Before Merging**:
   ```bash
   ./gradlew :kotstep:desktopTest :kotstep-sdui:desktopTest  # Multiplatform unit & UI tests
   ./gradlew :app:assembleDebug                             # Android smoke check
   ```
2. **UI Test Isolation**:
   - Desktop Compose UI tests must provide explicit container dimensions (e.g. `Modifier.width(400.dp)`) when testing horizontal layouts.
