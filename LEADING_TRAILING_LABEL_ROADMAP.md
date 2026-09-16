# Leading/Trailing Label Roadmap

Planned improvements for the V3 `leadingLabel` / `trailingLabel` API and layout behavior.

## Goals

- Jetpack Compose-first
- Easy to use for developers
- Flexible
- Lightweight
- Seamless
- Less recomposition
- Cool but controlled animations

## Current Observations

- The V3 API already supports `leadingLabel` and `trailingLabel` slots.
- Layout currently reserves cross-axis space so step indicators stay aligned across items.
- Reserved label space only grows to the largest measured size and does not naturally shrink back if content changes.
- `Step` currently stores mutable properties.
- DSL defaults use empty composables instead of `null` for optional slots.

## Cross-Framework Guidance

- Jetpack Compose / Material patterns usually use nullable optional slots such as `leadingIcon`, `trailingIcon`, `prefix`, `suffix`, and `supportingText`.
- Flutter commonly models step content with `title`, optional `subtitle`, and optional `label`.
- React Native libraries usually expose left/right accessory slots instead of label-specific APIs.
- Android XML / Material components expose explicit start/end/prefix/suffix APIs.
- SwiftUI stepper libraries tend to separate step content from spacing/alignment controls.

## Recommended Plan

### Phase 1: Low Risk

- Change DSL defaults from empty composables to `null` for truly optional slots.
- Make `Step` immutable (`val` instead of `var`) so Compose state is easier to reason about.
- Read `currentStep()` once per composition pass and derive state/progress from that snapshot.
- Add tests for no-label usage and general slot stability.

### Phase 2: Medium Risk

- Replace the current "max only grows" reserved-space measurement with per-step cached measurements.
- Derive the max reserved leading/trailing slot size from active measurements so layout can both grow and shrink correctly.

### Phase 3: API Polish

- Consider `leadingContent` / `trailingContent` aliases while keeping `leadingLabel` / `trailingLabel` for compatibility.
- Add a slot reservation strategy API such as `Natural`, `AlignToLargest`, or `Fixed(...)`.

### Phase 4: UX Polish

- Refine label and connector animations so indicator anchoring remains stable while slot content expands and collapses smoothly.
- Expand accessibility and layout coverage for long labels, RTL, dynamic type, and changing step content.

## Suggested Implementation Order

1. Phase 1 first for API correctness and Compose ergonomics.
2. Phase 2 next for layout correctness and recomposition safety.
3. Phase 3 after that if API surface changes are acceptable.
4. Phase 4 last as polish once behavior is stable.
