# 0001: V2 Legacy API Strategy During Compose Multiplatform Migration

## Context

KotStep has two API surfaces:

1. **V2 Legacy API**: sealed-class-based stepper (~26 files, e.g., `HorizontalStepper`, `VerticalStepper`, `StepperStyle`, `StepState`).
2. **V3 API**: modern DSL (`KotStep()`, `KotStepScope`, `step()`).

V2 relies heavily on Android-only dependencies (`androidx.constraintlayout:constraintlayout-compose` and `LocalContext`). Porting to `commonMain` means rewriting ConstraintLayout logic for Compose Multiplatform (CMP), which is high effort for an old API.

## Decision

**Keep V2 legacy API in `androidMain`. Do not port to `commonMain`.**
Deprecate V2, remove later.

## Rationale

- **Effort vs. Value**: V3 DSL supersedes V2. Rewriting legacy ConstraintLayout for CMP wastes time.
- **Published API Stability**: Existing Android consumers rely on V2. Dropping it breaks apps. Keeping it in `androidMain` protects Android users and gives time to migrate.
- **Clean Shared Code**: Confining V2 to `androidMain` keeps `commonMain` 100% free of `android.*` and `LocalContext`.

## Consequences

- V2 (`HorizontalStepper`, `VerticalStepper`) works on Android only.
- Desktop, Web, and iOS must use V3 DSL.
- V2 gets zero new features (maintenance mode).

## Deprecation Schedule

- **v3.2.0 (CMP Release)**: Mark V2 entry points (`HorizontalStepper`, `VerticalStepper`, `StepperStyle`, etc.) with `@Deprecated`. Point users to V3 DSL (`KotStep()`).
- **v4.0.0**: Remove V2 API completely. Drop `constraintlayout-compose` dependency.