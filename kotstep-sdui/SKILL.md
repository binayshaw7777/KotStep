---
name: kotstep-sdui
description: Guidance for designing, parsing, mutating, and rendering Server-Driven UI (SDUI) step flows using KotStep SDUI (:kotstep-sdui). Use when building dynamic backend-driven onboarding, e-commerce checkout, order tracking, and KYC steppers in Compose Multiplatform.
---

# KotStep SDUI Skill

KotStep SDUI (`:kotstep-sdui`) is a modular Server-Driven UI extension for KotStep in Compose Multiplatform (CMP). It decodes JSON step flow specifications from backend APIs or CMS, maintains a reactive state machine with optimistic updates and rollbacks, and renders rich, responsive steppers.

---

## 1. System Architecture

```
[ Backend API / Remote Config ]
              │ (JSON)
              ▼
      [ SduiParser ]
   (Lenient, Fault-Tolerant)
              │
              ▼
     [ SduiStateManager ] ◄── Optimistic User Actions
   (StateFlow<SduiFlow>)  ◄── Realtime Mutations (WebSocket / SSE)
              │
              ├── [ SduiColorResolver ] (Hex colors with safe fallbacks)
              ├── [ SduiIconResolver ]  (String icon keys -> ImageVectors)
              └── [ SduiContentResolver ] (Custom step composables)
              │
              ▼
       [ KotStepSdui ] (UI Adapter)
              │
              ▼
   [ KotStep Core v3 DSL ]
```

---

## 2. Step States & Visual Indicators

| State | Behavior & Visual Indicator |
|---|---|
| `TODO` | Inactive step, rendered using `todoColor`. |
| `CURRENT` | Active step, highlighted using `currentColor` with pulsing/focus. |
| `DONE` | Completed step, highlighted using `doneColor` with checkmark. |
| `ERROR` | Failed step validation, displays exclamation badge and error text. |
| `LOCKED` | Inaccessible step, displays padlock icon; clicks disabled. |
| `SKIPPED` | Optional or bypassed step, displays dash icon and muted label. |

---

## 3. Standard JSON Schema

### Minimal Example
```json
{
  "schemaVersion": "1.0.0",
  "flowId": "checkout_minimal",
  "version": 1,
  "steps": [
    { "id": "cart", "ordinal": 0, "title": "Cart", "state": "DONE" },
    { "id": "shipping", "ordinal": 1, "title": "Shipping", "state": "CURRENT" },
    { "id": "payment", "ordinal": 2, "title": "Payment", "state": "TODO" }
  ]
}
```

### Full Featured Example
```json
{
  "schemaVersion": "1.0.0",
  "flowId": "order_fulfillment",
  "version": 4,
  "currentStepIndex": 1.0,
  "allowDirectNavigation": false,
  "style": {
    "orientation": "VERTICAL",
    "step": {
      "shape": "CIRCLE",
      "sizeDp": 40.0,
      "colors": {
        "todo": "#B0BEC5",
        "current": "#1E88E5",
        "done": "#43A047",
        "error": "#E53935",
        "locked": "#78909C"
      }
    },
    "line": {
      "type": "SOLID",
      "thicknessDp": 3.0,
      "activeColor": "#43A047",
      "inactiveColor": "#CFD8DC"
    }
  },
  "steps": [
    {
      "id": "order_placed",
      "ordinal": 0,
      "title": "Order Placed",
      "subtitle": "Received at 10:30 AM",
      "state": "DONE",
      "indicator": { "type": "ICON", "iconName": "check" }
    },
    {
      "id": "in_transit",
      "ordinal": 1,
      "title": "In Transit",
      "subtitle": "Arriving tomorrow by 2 PM",
      "state": "CURRENT",
      "indicator": { "type": "ICON", "iconName": "local_shipping" }
    },
    {
      "id": "delivered",
      "ordinal": 2,
      "title": "Delivered",
      "subtitle": "Package at front door",
      "state": "TODO",
      "indicator": { "type": "NUMBER", "value": "3" }
    }
  ]
}
```

---

## 4. Client Integration Recipes

### A. One-Liner Drop-in
```kotlin
@Composable
fun SimpleSduiScreen(jsonPayload: String) {
    KotStepSdui(
        json = jsonPayload,
        modifier = Modifier.fillMaxWidth()
    )
}
```

### B. Interactive Flow with State Manager & Optimistic Advances
```kotlin
@Composable
fun OrderTrackingScreen(initialJson: String, viewModel: CheckoutViewModel) {
    val stateManager = remember { SduiStateManager(initialJson) }

    // Pluggable icon resolution
    val iconResolver = remember {
        SduiIconResolver { iconName ->
            when (iconName) {
                "shopping_cart" -> Icons.Default.ShoppingCart
                "local_shipping" -> Icons.Default.LocalShipping
                "payment" -> Icons.Default.Payment
                "check" -> Icons.Default.Check
                else -> null
            }
        }
    }

    KotStepSdui(
        stateManager = stateManager,
        iconResolver = iconResolver,
        onAction = { action, step ->
            when (action.type) {
                ActionType.NAVIGATE -> {
                    // 1. Optimistic client advance
                    stateManager.optimisticAdvance(step.id)
                    // 2. Call remote server
                    viewModel.submitStep(step.id, onFailure = {
                        // 3. Rollback on failure
                        stateManager.rollback()
                    })
                }
                ActionType.CUSTOM -> {
                    viewModel.handleCustomAction(action.payload)
                }
            }
        },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    )
}
```

---

## 5. Dynamic Runtime Mutations

The server can push targeted tree mutations over WebSockets, Server-Sent Events, or push notifications:

```kotlin
// Example: Insert customs step and update version
stateManager.applyMutations(
    mutations = listOf(
        SduiMutation.InsertStep(
            step = SduiStep(
                id = "customs",
                title = "Customs Declaration",
                subtitle = "Inspection pending",
                state = StepStateEnum.TODO
            ),
            afterStepId = "in_transit"
        )
    ),
    version = 5
)
```

Supported mutation types:
- `SduiMutation.InsertStep(step, afterStepId)`: Inserts step and re-normalizes ordinals.
- `SduiMutation.RemoveStep(stepId)`: Removes step and re-normalizes ordinals.
- `SduiMutation.UpdateStep(stepId, patch)`: Modifies step properties (title, state, subtitle, etc.).
- `SduiMutation.UpdateStyle(patch)`: Modifies layout orientation, shapes, or color palettes.

---

## 6. Best Practices & Rules

1. **Monotonic Version Gating**:
   - `SduiStateManager` automatically discards payloads where `version <= currentVersion`. Ensure backend increments the version with every state change or mutation.
2. **Graceful Fallbacks**:
   - `SduiColorResolver` must never throw on malformed hex strings; fall back to safe neutrals (`Color.DarkGray`).
   - `SduiIconResolver` returns `null` for unknown icons, falling back to ordinal number indicators.
3. **Decouple Indicator vs Label**:
   - Indicator is strictly the marker (number/icon/dot/padlock). Titles, subtitles, and errors belong in labels.
