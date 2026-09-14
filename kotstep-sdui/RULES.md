# KotStep SDUI Rules

These rules govern the architecture, serialization, state management, and UI rendering of `:kotstep-sdui`.

---

## 1. Schema Evolution & Fault Tolerance

1. **Parser Resilience**:
   - The JSON parser MUST always be configured with `ignoreUnknownKeys = true` and `isLenient = true`.
   - Incoming payloads with unexpected fields must parse cleanly without crashing the client.
2. **Backward & Forward Compatibility**:
   - All new schema fields must be optional with safe defaults (e.g. `val errorMessage: String? = null`).
   - Do NOT rename or alter the types of existing JSON keys (`id`, `ordinal`, `state`, `title`, `indicator`, `style`).

---

## 2. Versioning & Network Concurrency

1. **Monotonic Version Gating**:
   - Client state updates via `applyServerFlow()` and `applyMutations()` must strictly verify `newVersion > currentFlow.version`.
   - Any server payload or mutation with `version <= currentFlow.version` MUST be rejected to eliminate race conditions from WebSocket/push updates.
2. **Rollback Guarantee**:
   - `SduiStateManager` must cache the last confirmed server state (`lastServerFlow`).
   - Speculative/optimistic updates must be fully reversible via `rollback()` without state corruption or losing active UI observers.

---

## 3. State Machine & User Interaction

1. **LOCKED State Isolation**:
   - Steps with `StepStateEnum.LOCKED` must NEVER execute actions, navigate, or emit click events.
   - Clicking a locked step must be a silent no-op.
2. **Error State Visibility**:
   - Steps with `StepStateEnum.ERROR` must display an error badge and render `errorMessage` (if present) in high-contrast red below the subtitle.
3. **Ordinal Integrity**:
   - Whenever steps are inserted or removed dynamically via mutations, step ordinals MUST be re-indexed sequentially (`0, 1, 2, ...`) and `currentStepIndex` re-synchronized.

---

## 4. Resolver Safety & Fallbacks

1. **Color Fallbacks**:
   - `SduiColorResolver` must support `#RRGGBB`, `#AARRGGBB`, and `#RGB` hex formats.
   - Invalid or malformed color strings must NEVER throw exceptions; they must return a safe neutral fallback (`Color.DarkGray`).
2. **Icon Fallbacks**:
   - Unknown icon strings must resolve to `null`, gracefully falling back to ordinal number rendering.
3. **Android-Free `commonMain`**:
   - All resolvers, models, state managers, and UI components in `:kotstep-sdui/src/commonMain` must remain 100% Android-free.
