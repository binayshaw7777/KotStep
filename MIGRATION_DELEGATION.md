# KotStep → CMP — Agent Delegation & Conflict Resolution

This file governs **who works on what** when multiple agents (Claude Code, Antigravity, opencode, Codex) collaborate on `MIGRATION_PLAN.md` in parallel.

**The core principle: partition by file ownership, not by phase.**
Two agents only conflict when they touch the *same file*. If we partition the file tree so each agent owns disjoint paths and never edits outside them, parallel work is conflict-free **by construction** — and the "conflict resolution strategy" becomes mostly a prevention + arbitration protocol instead of a messy merge exercise.

---

## 1. Roles

| Role | Default assignee | Responsibility |
|---|---|---|
| **Navigator** (arbiter) | opencode (this session) | Owns the build-touchpoint files; runs ALL build/test verification; owns `git commit`; updates `MIGRATION_CHECKLIST.md` statuses; arbitrates conflicts |
| **Builder(s)** | Claude Code, Antigravity, Codex | Implement tasks inside their owned file paths; report back; never commit |
| **Auditor** | Navigator or a builder on the *opposite* track | Grep-audit for Android leaks in `commonMain` (cross-track review) |
| **Reviewer** | `caveman-review` or a builder on a different track | Diff review vs gate criteria |

Non-negotiable: **builds are verified ONLY by the Navigator.** A builder's claim "it compiles" is never trusted — the Navigator re-runs the Gradle command itself before merging. This kills the most common source of cross-agent friction (blaming the wrong agent for a red build).

---

## 2. File Ownership Map (single writer per path)

```
OWNERSHIP REGISTRY — "OWNS" = may create/edit. All other agents READ-ONLY.

/ (repo root)

  build.gradle.kts ................................ NAVIGATOR ONLY          (serial)
  settings.gradle.kts ............................. NAVIGATOR ONLY          (serial)
  gradle.properties ............................... NAVIGATOR ONLY          (serial)
  gradle/libs.versions.toml ....................... NAVIGATOR ONLY          (serial)
  gradle/wrapper/* ................................ NAVIGATOR ONLY          (serial)
  AGENTS.md ....................................... NAVIGATOR ONLY
  MIGRATION_*.md .................................. NAVIGATOR ONLY
  README.md ....................................... DOC-TRACK-RW  (anyone, sequenced by Navigator)

  kotstep/build.gradle.kts ........................ NAVIGATOR ONLY          (serial)
  kotstep/src/commonMain/ ......................... CORE-TRACK   (one builder at a time)
  kotstep/src/commonMain/composeResources/ ........ CORE-TRACK   (one builder at a time)
  kotstep/src/androidMain/ ........................ CORE-TRACK legacy keep (one builder at a time)
  kotstep/src/iosMain/ ............................ IOS-TRACK (only Antigravity)
  kotstep/src/jvmMain/ ............................ (empty; reserved)
  kotstep/src/wasmJsMain/ ......................... (empty; reserved)
  kotstep/src/commonTest/ ......................... TEST-TRACK  (only Claude Code)
  kotstep/src/androidInstrumentedTest/ ............ TEST-TRACK  (only Claude Code)

  app/** .......................................... APP-TRACK (only opencode)  [demo app + android theme]
  desktopApp/** ................................... DESKTOP-TRACK (only Claude Code)
  webApp/** ....................................... WEB-TRACK (only Antigravity)
  iosApp/** ....................................... IOS-TRACK (only Antigravity)

  docs/decisions/* ................................ DOC-TRACK-RW
  .github/workflows/* ............................. NAVIGATOR ONLY          (CI is a build touchpoint)
  jitpack.yml ..................................... NAVIGATOR ONLY
```

**Colour-code rule of thumb:**
- 🟦 **NAVIGATOR-only** = any file Gradle/settings/CI touch → serial, the critical path.
- 🟩 **Track-owned** = everything under one path owned by exactly one builder → fully parallel.
- 🟨 **DOC-TRACK-RW** = markdown outside `MIGRATION_*.md` → anyone may draft, **Navigator merges** the content to avoid write-write races.

> **Admittance rule (THE critical-path serial gate):** Build files (`libs.versions.toml`, root & module `build.gradle.kts`, `settings.gradle.kts`, wrapper) have literally ONE writer (Navigator). Builders do **not** amend build logic; they request build changes via a **"build request"** ticket (see §5) and Navigator applies them. This is what keeps Gradle plumbing conflict-free.

---

## 3. Parallelism Plan (workstreams)

Three agents can genuinely run in parallel from **Phase 1 onwards**, because the decoupling is by path, and each phase keeps producing owned paths that unlock parallel work.

```
WAVE            ACTIVE TRACKS (parallel)                            SERIAL SPINE
────            ───────────────────────                              ───────────
Phase 1         [START]  none yet                                                   
                CORE-TRACK (Claude): Samples.kt, resources, logic  ── NAVIGATOR:
                extraction, V2 decision draft                       ──   T-1.1→T-1.5 toolchain
                            │ concurrent                            ──   (libs.versions.toml,
                            │                                        ──    wrapper, root g.kts)
Phase 2         CORE-TRACK (Claude): V3 port, common/Android move   ── NAVIGATOR:
                TEST-TRACK (Claude, AFTER merge gate 2.5): ports    ──   T-2.5 compile gate
                                                                     ──   → merge → unlock CORE
Phase 3         CORE-TRACK (Claude): commonMain purge               ── NAVIGATOR:
                DOC-TRACK (Any, cc opencode): README draft,         ──   T-3.3 grep audit gate
                V2 decision                                          ──   → merge → unlock
Phase 4–6       DESKTOP-TRACK (Claude): desktopApp module           ── NAVIGATOR:
                WEB-TRACK (Antigravity): webApp module              ──   T-4.1 add jvm target,
                IOS-TRACK (Antigravity): iosApp scaffold            ──   T-5.1 add wasm target,
                APP-TRACK (opencode): share demo UI                 ──   T-6.1 add ios targets
                DOC-TRACK                                               → branch per entrypoint
Phase 7         TEST-TRACK (Claude): CMP UI tests + unit tests      ── NAVIGATOR:
                DOC-TRACK                                                T-7.1 merge gate
Phase 8         NAVIGATOR: T-8.1 publish block,          ── NAVIGATOR:
                T-8.2 jitpack, T-8.3 `cmp-ci.yml`             (CI/workflows are build
                (serial spine; see CI ownership note)         touchpoints)
Phase 9         DOC-TRACK (all): README platform docs               ── NAVIGATOR: version bump
Phase 10        AUDIT (Navigator): perf/a11y sweep                  ── NAVIGATOR: dep removal
```

**Key unlock points (gates that hand work over):**
1. End of **T-1.5** → CORE-TRACK can start (Claude) while Navigator does… (actually P1/P2 overlap: Navigator adds target scaffolding, Claude preps Samples/resource moves in `commonMain` — allowed because paths differ).
2. End of **T-2.5** (compile gate) → TEST-TRACK, CORE-TRACK concrete moves.
3. End of **T-3.3** (grep audit green) → DESKTOP/WEB/IOS/APP all start at once — the big parallel burst.
4. End of **T-7.1** → CI/dashboard work.

> **Parallel burst (Phases 4–6) is where this design wins:** once `commonMain` is Android-free and targets are declared, the four entrypoints are *independent directories* (`desktopApp/**`, `webApp/**`, `iosApp/**`, `app/**`) each owned by a different agent. Zero overlap → zero git conflicts. All four can be built in the same minutes.

---

## 4. Agent Assignments (suggested)

| Agent | Track(s) | Owned paths | Strength to use |
|---|---|---|---|
| **opencode** (this session) | NAVIGATOR + APP-TRACK | every build/settings/CI file, repo-root docs, `app/**` | Arbitration, verification, keeping `main` green |
| **Claude Code** (`claude` CLI) | CORE-TRACK, TEST-TRACK, DESKTOP-TRACK | `kotstep/src/commonMain/**`, `commonTest/**`, `androidInstrumentedTest/**`, `desktopApp/**` | Large refactors, careful source-set moves, CMP test authoring |
| **Antigravity** (`agy` CLI) | WEB-TRACK, IOS-TRACK | `webApp/**`, `iosApp/**` + `kotstep/src/iosMain/**` | Scaffolding new platform modules, Xcode project |

> **CI ownership (resolved Phase 8):** earlier drafts assigned `.github/workflows/**` to Antigravity (CI-TRACK). AGENTS.md Hard Rule #1 (Navigator owns CI workflows) takes precedence → **Navigator authors and owns all workflows** (`cmp-ci.yml` included). Antigravity may *review* workflows but never edit them; file a ticket instead.
| **Codex** (optional 4th) | only if signal-to-noise is high; reserve for one-off isolated tasks (e.g. specific file lints, a single test authoring) | any path not currently owned | Burst capacity on a *finished* track (never overlapping) |

Each entry in the checklist should record **WHO** did it (add `Owner:` col). Agreed mapping so far:
- T-1.x T-2.x, T-3.x(build) → **opencode/Navigator**
- T-3.x(code): Samples.kt, resources, V2 conv → **Claude Code**
- T-4.x → **Claude Code**
- T-5.x → **Antigravity**
- T-6.x → **Antigravity**
- T-7.x → **Claude Code**
- T-8.x: publish block + `cmp-ci.yml` → **opencode/Navigator** (per CI ownership note above)
- T-9.x docs → **whoever acquired the path; Navigator commits**

*Adjust freely — the ownership map is the truth, not this table.*

---

## 5. Conflict Resolution Strategy (Arbitration Protocol)

Because ownership is partitioned, most "conflicts" never happen. What remains:

### 5.1 Prevention (mandatory)
1. **No builder edits outside owned paths.** `git status` + `git diff` shows *owned-by-another* changes → the builder stops and files a ticket instead. Enforced at review.
2. **Navigator performs all build-file edits** (§2 🟦). A builder needing a dependency/plugin/target change files a **build request** (see below) rather than editing `libs.versions.toml` or a module `build.gradle.kts`.
3. **One commit per task** (per plan). Navigator commits; builders hand back working-tree state + checklist note, never a commit.
4. **Rebase discipline:** before resuming a task, `git pull --rebase` — Navigator keeps `main` as the single integration point.
5. **Never merge two agents' work in the same commit for the same functional area** (e.g. two agents both touching `commonMain` semantics).

### 5.2 Build-Request ticket (the primary hand-off mechanism)
A builder needing build-level change opens an issue-style note (append to a running `docs/decisions/BUILD-REQUESTS.md` or a GitHub issue):
```
TYPE: add-target | add-dep | bump-version | move-source-set
WHY: <task no. + one-liner>
DETAIL: <exact code snippet>
BLOCKS: <task that can't proceed until applied>
```
Navigator applies it, re-runs the gated build, and replies DONE = target/false. This is the serial pipe; it's only items that strictly need Gradle — everything else flows parallel.

### 5.3 Arbitration ladder (when two agents DO collide)
1. **Detect:** `git status` shows overlap → whoever touches a non-owned path **backs out immediately** (revert own changes in that path only, keep the rest). Retreating is cheaper than fighting; the work is re-applied by the owner after.
2. **Escalate to Navigator:** Navigator decides ownership (may adjust the registry), applies conflict-free consolidation, re-runs gate.
3. **Re-sequence:** if a task genuinely needs a file owned by another track (e.g. Claude's `desktopApp` needs Antigravity's `DemoApp`), Navigator **reorders** the checklist (move gate earlier) rather than allowing shared writes. Dependency reordering, not shared editing.
4. **Break-glass:** checkpoint at `migrate-baseline` tag; worst case `git checkout` that tag and rebase the divergent track **onto** the checkpoint — never `git merge --abort` both tracks blindly.
5. **Never `force-push`.** History is the audit log; a conflict is resolved in new commits, not by rewriting shared history.

### 5.4 What counts as "DONE" for a task (no argument left open)
- Builder: checklist row updated (`Owner`, `Verify command`, `Pass/Fail`).
- Navigator: gate command re-run **by Navigator**; `grep -rn "android\\." kotstep/src/commonMain` empty where applicable.
- Reviewer (different track): diff looked at; any violation → ticket, task returns `[~]`.
- Only then: `[x]` and a commit.

---

## 6. Kicking off right now (Phase 1 first 15 minutes)

1. **Navigate session (this one):** baseline tag + branch:
   ```bash
   git tag migrate-baseline $(git rev-parse HEAD)
   git switch -c feat/compose-multiplatform
   ```
2. **Parallel tasks for Phase 1 (no gradle writes yet):**
   - Claude Code (Core Track): audit `Samples.kt` + resource references and propose the exact move list (no edits to build files).
   - Antigravity (Doc Track): draft README platform section + V2 decision memo in `docs/decisions/`.
   - opencode (Navigator): do build-request #1 (wrapper bump + catalog) — the only serial piece.
3. Next gated burst after T-1.5 → hand work to Claude Core Track + test prep.

The first genuinely conflict-free parallel wave is **Phase 4–6 burst** (desktop/web/iOS/app in parallel). Everything before is a doc/audit parallel layer over a mostly-serial toolchain ramp.

---

*Source of truth: `MIGRATION_PLAN.md`. Checklist statuses live in `MIGRATION_CHECKLIST.md`. This document extends `MIGRATION_AGENT_BRIEFS.md` with the delegation/ownership protocol.*