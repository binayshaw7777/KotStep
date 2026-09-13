# AGENTS.md

## Project

**KotStep** — a customizable stepper UI library. Currently Android-only (Jetpack Compose, JitPack `com.github.binayshaw7777:KotStep`), mid-migration to **Compose Multiplatform** (Android / iOS / Desktop / Web).

- Library module: `:kotstep` (v3 DSL API is the product; v2 legacy sealed-class API still shipped)
- Demo app: `:app` (Android)
- Version catalog: `gradle/libs.versions.toml` | Kotlin 2.1.10, AGP 8.3.x, Gradle 8.4+

## Migration is the current primary effort

If the task touches build files, module layout, source-set placement, or platform targets, read these FIRST:

| Doc | Purpose |
|---|---|
| `MIGRATION_PLAN.md` | Full end-to-end plan: phases T-1.1 → T-10.4, risk register, exit criteria |
| `MIGRATION_CHECKLIST.md` | Executable task checklist (tick boxes per task) |
| `MIGRATION_AGENT_BRIEFS.md` | Paste-ready prompts for builder/reviewer agents per phase |
| `MIGRATION_DELEGATION.md` | **Multi-agent protocol**: file-ownership map, workstreams, conflict-resolution strategy |

**Work in one task = one green commit. Never batch phases. Update the checklist as you go.**

**Multi-agent rules (read `MIGRATION_DELEGATION.md` first if 2+ agents are active):**
1. Never edit build files (`libs.versions.toml`, root/module `build.gradle.kts`, `settings.gradle.kts`, wrapper, CI workflows, `jitpack.yml`) — the Navigator (opencode) owns all of them. File a build-request instead.
2. Only the Navigator runs Gradle verification for gates; builders report, don't merge.
3. Stay inside your owned paths (see registry in `MIGRATION_DELEGATION.md`). If two agents would touch the same file, the second agent re-sequences or escalates — never shared-write.

## Skills (installed for all agents)

Always invoke relevant skills for Kotlin/Compose/KMP work:

- `kmp-compose-multiplatform` — KMP architecture, source sets, expect/actual, version catalog, publishing
- `compose-skill` — Compose/CMP patterns, resources (`R`→`Res`), testing, navigation, performance
- `compose-multiplatform-patterns` — state mgmt, theming, KMP platform UI

Skills live in `~/.agents/skills/<name>` and are symlinked into `~/.claude/skills`, `~/.codex/skills`, `~/.cursor/skills`, and project `.claude/skills` + `.opencode/skills` (gitignored).

## Hard rules for this codebase

1. **`commonMain` must stay Android-free**: no `android.*`, `R.`, `LocalContext`, `Toast`, or `java.*` imports. Audit with:
   `grep -rn "android\\." kotstep/src/commonMain` (must be empty).
2. **API stability**: the V3 DSL (`KotStep()`, `KotStepScope`, `step()`, styles) is published — never break Android consumers.
3. **V2 legacy** stays in `androidMain` (needs `constraintlayout-compose`); do not port it to commonMain.
4. Samples/demos do not belong in the published library — they live in demo entrypoints (`app`, `desktopApp`, `webApp`, `iosApp`).
5. Resources use CMP `composeResources/` + generated `Res.*`, not Android `res/` + `R.*` (Phase 3).

## Build / verify

```bash
./gradlew :kotstep:assembleDebug :app:assembleDebug    # Android smoke (Phases 1–3)
./gradlew :desktopApp:run                              # Desktop (Phase 4)
./gradlew :webApp:wasmJsRun                            # Web (Phase 5)
./gradlew :kotstep:linkDebugFrameworkIosSimulatorArm64 # iOS framework (Phase 6)
./gradlew :kotstep:desktopTest                         # Shared tests (Phase 7)
```

Do not run `publishToMavenLocal` or CI-significant tasks unless a phase requires it.

## Testing

Shared `commonTest` (kotlin.test, CMP `createComposeRule`); Android-specific variants in `androidInstrumentedTest`; pure-logic unit tests in `commonTest`. See skill `compose-skill` → `testing.md`.

## Current phase status

- [x] Phase 0 audit
- [ ] Phase 1 toolchain  → start here (see `MIGRATION_PLAN.md` / `MIGRATION_CHECKLIST.md`)