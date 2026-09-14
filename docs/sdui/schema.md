# SDUI JSON Schema

KotStep SDUI adheres to a standard, versioned JSON Schema designed to be language-agnostic across backend systems (Node.js, Go, Python, Java, Kotlin).

Download the formal schema: [`kotstep-sdui-schema.json`](../kotstep-sdui-schema.json).

---

## Complete JSON Specification Example

```json
{
  "schemaVersion": "1.0",
  "flowId": "checkout_flow",
  "title": "Express Checkout",
  "stateModel": "CLIENT_OPTIMISTIC",
  "orientation": "HORIZONTAL",
  "currentStepId": "step_payment",
  "currentStepProgress": 0.0,
  "stateVersion": 2,
  "style": {
    "itemPaddingDp": 8,
    "showCheckMarkOnDone": true,
    "stepStyle": {
      "todo":    { "colorHex": "#475569", "sizeDp": 32, "shape": "CIRCLE" },
      "current": { "colorHex": "#3B82F6", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#1D4ED8" },
      "done":    { "colorHex": "#10B981", "sizeDp": 32, "shape": "CIRCLE" },
      "error":   { "colorHex": "#EF4444", "sizeDp": 36, "shape": "CIRCLE", "borderWidthDp": 2, "borderColorHex": "#B91C1C" },
      "locked":  { "colorHex": "#64748B", "sizeDp": 32, "shape": "CIRCLE" }
    },
    "lineStyle": {
      "todo":    { "lineColorHex": "#334155", "progressColorHex": "#334155", "thicknessDp": 3, "lineType": "SOLID" },
      "current": { "lineColorHex": "#334155", "progressColorHex": "#3B82F6", "thicknessDp": 3, "lineType": "SOLID" },
      "done":    { "lineColorHex": "#10B981", "progressColorHex": "#10B981", "thicknessDp": 3, "lineType": "SOLID" }
    }
  },
  "steps": [
    {
      "id": "step_cart",
      "ordinal": 0,
      "state": "DONE",
      "title": "Cart",
      "subtitle": "3 items • $129.99",
      "indicator": { "type": "ICON", "value": "shopping_cart" }
    },
    {
      "id": "step_shipping",
      "ordinal": 1,
      "state": "DONE",
      "title": "Shipping",
      "subtitle": "Express Priority • $15.00",
      "indicator": { "type": "ICON", "value": "local_shipping" }
    },
    {
      "id": "step_payment",
      "ordinal": 2,
      "state": "CURRENT",
      "title": "Payment",
      "subtitle": "Visa ending in •••• 4242",
      "indicator": { "type": "ICON", "value": "payment" },
      "action": { "type": "NAVIGATE", "target": "payment_sheet" }
    },
    {
      "id": "step_review",
      "ordinal": 3,
      "state": "TODO",
      "title": "Review",
      "subtitle": "Total: $144.99",
      "indicator": { "type": "ICON", "value": "receipt" }
    }
  ]
}
```

---

## Field Reference

### Flow Fields

| Property | Type | Description |
|---|---|---|
| `schemaVersion` | `String` | Semantic schema version (e.g. `"1.0"`). |
| `flowId` | `String` | Unique flow identifier. |
| `title` | `String?` | Optional display title for the flow. |
| `orientation` | `String` | `"HORIZONTAL"` or `"VERTICAL"`. |
| `currentStepId` | `String` | ID of the step currently in focus. |
| `currentStepProgress` | `Float` | Sub-step animated progress (0.0 to 0.999). |
| `stateVersion` | `Int` | Monotonically increasing sequence number for version conflict safety. |
| `steps` | `Array<SduiStep>` | List of step objects. |

### Step Fields

| Property | Type | Description |
|---|---|---|
| `id` | `String` | Unique step ID. |
| `ordinal` | `Int` | Zero-based sequence index. |
| `state` | `String` | `"TODO"`, `"CURRENT"`, `"DONE"`, `"ERROR"`, `"LOCKED"`, `"SKIPPED"`. |
| `title` | `String` | Main title text. |
| `subtitle` | `String?` | Secondary caption. |
| `leadingText` | `String?` | Text above (horizontal) or before (vertical) the indicator (e.g. timestamp). |
| `errorMessage` | `String?` | Error description rendered in red when state is `ERROR`. |
| `indicator` | `Object` | Specifies indicator type (`DEFAULT`, `NUMBER`, `TEXT`, `ICON`, `CUSTOM`). |
| `action` | `Object?` | Action emitted on step interaction (`NAVIGATE`, `CUSTOM`). |
