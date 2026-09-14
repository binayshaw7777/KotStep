# Welcome to KotStep

**KotStep** is a high-performance, customizable Stepper UI library designed for **Compose Multiplatform (CMP)**. Build beautiful linear or non-linear multi-step workflows across **Android**, **iOS**, **Desktop (JVM)**, and **Web (Wasm)** from a single, shared Kotlin codebase.

KotStep ships in two modules:
1. **`:kotstep` (Core Stepper)**: A modern, declarative V3 DSL for Compose with smooth animations, custom indicator slots, leading/trailing labels, and horizontal/vertical layouts.
2. **`:kotstep-sdui` (Server-Driven UI)**: A reactive, schema-driven SDUI layer that renders and updates complete stepper flows directly from backend JSON with optimistic client advance, rollback on failure, and realtime push mutations.

---

## 🚀 Key Highlights

<div class="grid cards" markdown>

-   :material-devices: __Compose Multiplatform__

    ---

    True write-once, run-anywhere UI across Android (API 24+), iOS 16+, Desktop JVM 17, and Web (Wasm-GC).

-   :material-tune: __Declarative V3 DSL__

    ---

    Intuitive Compose syntax: `KotStep(currentStep = { step }) { step { ... } }` with sub-pixel animated progress lines.

-   :material-server: __Server-Driven UI (SDUI)__

    ---

    Decouple UI from app release cycles. Control onboarding, checkout, KYC, and order tracking flows dynamically from backend JSON.

-   :material-lightning-bolt: __Optimistic State Engine__

    ---

    Instant client-side step progression with automatic server confirmation and instant rollback on network failures.

-   :material-auto-fix: __Realtime Tree Mutations__

    ---

    Inject mid-flow steps, remove steps, update titles, or adjust themes on the fly via WebSockets, SSE, or push notifications.

-   :material-eye-outline: __Accessibility & Edge-Cases__

    ---

    Full ScreenReader semantics, state descriptions, 48dp touch targets, and constraint-safe layouts.

</div>

---

## 📦 Modules at a Glance

| Module | Purpose | Targets | Dependencies |
|---|---|---|---|
| **`:kotstep`** | Core V3 Stepper DSL & layout primitives | Android, iOS, Desktop, Web | Compose Runtime, UI, Foundation |
| **`:kotstep-sdui`** | Server-Driven UI, parser, state manager, resolvers | Android, iOS, Desktop, Web | `:kotstep`, `kotlinx-serialization-json` |

---

## 🏁 Getting Started in 30 Seconds

```kotlin
// Android, Desktop, Web, iOS
var currentStep by remember { mutableStateOf(0f) }

KotStep(
    currentStep = { currentStep },
    style = KotStepStyle(stepLayoutStyle = StepLayoutStyle.Horizontal)
) {
    step(title = "Cart", onClick = { currentStep = 0f })
    step(title = "Address", onClick = { currentStep = 1f })
    step(title = "Payment", onClick = { currentStep = 2f })
    step(title = "Review", onClick = { currentStep = 3f })
}
```

Or render it directly from your API response with SDUI:

```kotlin
KotStepSdui(
    json = apiResponseJson,
    modifier = Modifier.fillMaxWidth()
)
```
