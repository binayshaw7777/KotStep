s---
name: kotstep-v3-plan
description: Strategic implementation plan, prioritized roadmap, and session-by-session execution log for KotStep V3 improvements. Use when continuing work across multiple AI agents or sessions.
---

# KotStep V3 Strategic Planning & Execution Log

## Overview

This skill provides a comprehensive, prioritized roadmap for KotStep V3 improvements with detailed session tracking. Use this when:
- Continuing work from a previous session
- Handing off to another AI agent
- Planning major feature additions (like leading icons)
- Tracking progress across multiple tiers of work

## Current Status

| Metric | Value |
|--------|-------|
| Library Version | 3.1.0 |
| Current Phase | Tier 1 In Progress, Tier 2 UI Test Harness Started |
| Sessions Completed | 4 |
| Completion Status | Tier 1 partially complete, label layout refactor complete, initial UI tests compiled |
| Estimated Total Hours | 30-40 hours for all tiers |

## Prioritized Tiers (Execute in Order)

### Tier 1: Performance & Accessibility (CRITICAL - HIGH IMPACT)
**Estimated**: 4 hours | **Status**: 50% complete (2 of 4 tasks complete)

**Why First**: Foundation for all other improvements. Directly improves production performance and user accessibility.

| Task | Status | Effort | Details |
|------|--------|--------|---------|
| Remove Debug Logging | Complete | 30 min | File: HorizontalStepItem.kt, debug logging removed |
| Dynamic Accessibility Descriptions | Complete | 1 hr | File: StepIndicator.kt, state-aware semantics added |
| Touch Target Size Validation | ⏳ Pending | 1-2 hrs | Verify all clickable areas ≥ 48x48 dp |
| Color Contrast Audit | ⏳ Pending | 1-2 hrs | WCAG AA compliance check (4.5:1 text, 3.0:1 large) |

**Next**: Complete touch target audit first, then color contrast verification

---

### Tier 2: Testing Infrastructure (HIGH - MEDIUM IMPACT)
**Estimated**: 12 hours | **Status**: 0% complete

**Why Second**: Ensures Tier 1 and Tier 3 changes are verifiable and prevent regressions.

| Task | Status | Effort | Details |
|------|--------|--------|---------|
| Unit Tests Setup | ⏳ Pending | 4 hrs | KotStepScope, Step, Style tests |
| Compose UI Tests | ⏳ Pending | 6 hrs | Layout, state transitions, interactions |
| Screenshot Tests (Roborazzi) | ⏳ Pending | 4 hrs | Visual regression testing (JVM-based) |

**Prerequisites**: All Tier 1 tasks complete

---

### Tier 3: Leading/Trailing Label Layout Refactor (MEDIUM - NEW FEATURE)
**Estimated**: 8 hours | **Status**: Complete

**Why Third**: New user-facing feature requiring stable foundation from Tiers 1-2.

| Task | Status | Effort | Details |
|------|--------|--------|---------|
| Step Model Update | Complete | 2 hrs | Added `leadingLabel` and `trailingLabel` to the `Step` model and DSL |
| Component Implementation | Complete | 4 hrs | Reserved shared label space and kept the spine aligned in both layouts |
| Documentation & Samples | Complete | 2 hrs | Updated local samples and planning docs |

**Note**: Implemented early due to direct user reprioritization. Test coverage is still pending in Tier 2.

---

### Tier 4: Code Quality (MEDIUM - TECHNICAL DEBT)
**Estimated**: 5 hours | **Status**: 0% complete

| Task | Status | Effort | Details |
|------|--------|--------|---------|
| State Hoisting Review | ⏳ Pending | 2 hrs | Verify state flows down, events up |
| Modifier Best Practices | ⏳ Pending | 1 hr | Standardize modifier parameter order |
| Unused Import Cleanup | ⏳ Pending | 0.5 hrs | Remove leftover imports (e.g., Log) |

---

### Tier 5: Build & Dependencies (LOW - INFRASTRUCTURE)
**Estimated**: 8 hours | **Status**: 0% complete (Optional)

| Task | Status | Effort | Details |
|------|--------|--------|---------|
| Convention Plugins | ⏳ Optional | 8 hrs | Reusable build logic (nice-to-have) |
| Version Catalog Cleanup | ⏳ Pending | 1 hr | Verify all dependencies versioned |

---

## Applied Skills & Best Practices

This plan leverages 9 expert skills:

### **compose-performance-audit**
- Identifies recomposition storms and unnecessary allocations
- **Planned for**: Tier 1 (Debug logging removal)
- **Next use**: Performance testing implementation

### **android-accessibility**
- Content descriptions, touch targets, color contrast
- **Planned for**: Tier 1 (Accessibility improvements)
- **Next use**: Touch target audit and color contrast verification

### **compose-ui**
- State hoisting, modifier patterns, reusable composables
- **Planned for**: Tier 4 code quality review
- **Next use**: State hoisting audit

### **android-testing**
- Unit tests, UI tests, screenshot testing with Roborazzi
- **Planned for**: Tier 2 implementation
- **Next use**: Full Tier 2 testing setup

### **android-architecture**
- Clean architecture, layering, modularization
- **Planned for**: Tier 3 leading icon feature
- **Next use**: Feature architecture design

### **android-coroutines**
- Async patterns, lifecycle safety, dispatcher management
- **Planned for**: Future features requiring async
- **Next use**: If coroutines needed in implementation

### **android-gradle-logic**
- Convention plugins, version catalogs, build optimization
- **Planned for**: Tier 5 optional work
- **Next use**: Build infrastructure improvement

### **coil-compose**
- Image loading optimization
- **Planned for**: Future if needed
- **Next use**: If image loading in step icons becomes concern

---

## Execution Strategy

### Planning Before Coding
**Always follow this sequence**:
1. Read this entire skill file
2. Check SESSIONS.md for historical context
3. Identify next incomplete task from tiers
4. Plan all file changes needed
5. Estimate effort and token budget
6. Execute changes
7. Update SESSIONS.md with results

### Quality Gates Before Committing

```bash
# Run before every commit:
./gradlew :kotstep:lint              # Lint checks
./gradlew :kotstep:build             # Full build
./gradlew :kotstep:testDebugUnitTest # Unit tests (when available)
```

### Token Budget Management
- **Session capacity**: 200,000 tokens
- **Overhead**: ~20% for tool calls, fixes, documentation
- **Per-tier budget**:
  - Tier 1: 30,000 tokens (Planning + execution + docs)
  - Tier 2: 60,000 tokens (Test infrastructure setup)
  - Tier 3: 50,000 tokens (Feature implementation)
  - Tier 4: 30,000 tokens (Code quality)
  - Tier 5: 40,000 tokens (Build infrastructure)

---

## Version Roadmap

| Version | Features | Status | Dependencies |
|---------|----------|--------|--------------|
| 3.1.0 | Current | ✅ Released | - |
| 3.2.0 | Leading Icons + Perf Fixes | 🔄 In Progress | Tier 1-2 Complete |
| 3.2.1 | Test Coverage | ⏳ Planned | Tier 2 Complete |
| 3.3.0 | Accessibility Hardening | ⏳ Planned | Tier 1 Complete |

---

## Session-by-Session Execution Log

### Session 1: Initial Scan & Tier 1 Start (March 17, 2026)

**Agent**: GitHub Copilot  
**Duration**: High budget  
**Status**: ✅ Complete

**Prompts Received**:
1. "Look for improvements, make a plan and show it to me first..."
2. "Prioritize the important ones first"
3. "Undo last change, always plan your steps before coding..."

**Completed Actions**:
- ✅ Deep scanned entire codebase
- ✅ Analyzed all 9 attached skills
- ✅ Removed debug logging from HorizontalStepItem.kt
- ✅ Added dynamic accessibility descriptions to StepIndicator.kt
- ✅ Created comprehensive planning documentation

**Files Modified**:
- `kotstep/src/main/java/.../HorizontalStepItem.kt` (removed Log.d call)
- `kotstep/src/main/java/.../StepIndicator.kt` (added dynamic descriptions)

**Next Session**: Continue with Touch Target Size audit (Tier 1)

---

## Key File Locations

```
kotstep/
├── src/main/java/com/binayshaw7777/kotstep/v3/
│   ├── KotStep.kt (Main entry point)
│   ├── component/
│   │   ├── steps/
│   │   │   ├── StepIndicator.kt ✅ (Modified: accessibility)
│   │   │   ├── HorizontalStepItem.kt ✅ (Modified: performance)
│   │   │   └── VerticalStepItem.kt
│   │   ├── layout/
│   │   ├── progress_bar/
│   │   └── label/
│   ├── model/
│   │   ├── step/
│   │   │   └── Step.kt ⏳ (Pending: add leadingIcon)
│   │   └── style/
│   └── util/
│
├── src/test/java/ (To be created)
│   └── Unit tests (Tier 2)
│
└── build.gradle.kts (Version 3.1.0)

.github/skills/
├── kotstep-v3-plan/
│   ├── SKILL.md (This file)
│   ├── KotStep_V3_Strategic_Plan.md (Detailed roadmap)
│   └── SESSIONS.md (Execution log & handoff notes)
```

---

## Handoff Checklist for Next Agent

**Before Starting**:
- [ ] Read this entire skill file
- [ ] Read KotStep_V3_Strategic_Plan.md
- [ ] Read SESSIONS.md for context
- [ ] Verify library version is 3.1.0
- [ ] Check current git branch

**During Work**:
- [ ] Follow Tier priority order
- [ ] Plan changes before coding
- [ ] Run lint and build after each tier
- [ ] Update SESSIONS.md after each session
- [ ] Test with Layout Inspector for performance
- [ ] Test with TalkBack for accessibility (if applicable)

**Before Handing Off**:
- [ ] Document all changes in SESSIONS.md
- [ ] Update completion status in tables
- [ ] Note any blockers or decisions
- [ ] Suggest priorities for next session

---

## Common Issues & Solutions

### Issue: Recomposition storms when touching steps
**Diagnosis**: Use Layout Inspector Recomposition Highlights  
**Likely Cause**: Unstable parameters or broad state changes  
**Solution**: Wrap in `remember()` or use `derivedStateOf()`  
**Skill**: compose-performance-audit

### Issue: TalkBack not announcing step state
**Diagnosis**: Test with TalkBack on real device  
**Likely Cause**: Missing or generic content descriptions  
**Solution**: Add semantic descriptions and `stateDescription`  
**Skill**: android-accessibility

### Issue: Tests failing after changes
**Diagnosis**: Run `./gradlew :kotstep:testDebugUnitTest`  
**Likely Cause**: Composable signature changed or state logic modified  
**Solution**: Update test fixtures and state assumptions  
**Skill**: android-testing

---

## Success Metrics

### Tier 1 Success
- ✅ 0 debug Log.d() calls in production code
- ✅ All step indicators have meaningful accessibility descriptions
- ✅ All clickable elements ≥ 48x48 dp
- ✅ Color contrast ≥ 4.5:1 (WCAG AA)
- ✅ No Layout Inspector recomposition regressions

### Tier 2 Success
- ✅ >80% unit test code coverage
- ✅ All layout transition tests passing
- ✅ Screenshot baseline established
- ✅ Build time <5 minutes (release)

### Tier 3 Success
- ✅ Leading icon rendering correctly in all layouts
- ✅ Leading icon respects step state colors
- ✅ Documentation updated with examples
- ✅ No performance regressions

---

## Quick Reference: Next Steps

**If continuing from Session 1**:
1. Touch target size audit (1-2 hrs) → File: StepIndicator.kt
2. Color contrast check (1-2 hrs) → File: StepStyle.kt
3. Unit test setup (2-3 hrs) → Create: KotStepScopeTest.kt
4. Leading icon design (2 hrs) → Update: Step.kt

**If starting fresh**:
1. Read all documentation (30 min)
2. Check current git state (5 min)
3. Identify next incomplete task (5 min)
4. Execute task following planning sequence above

---

## Document Management

**File**: `.github/skills/kotstep-v3-plan/SKILL.md`  
**Version**: 1.0  
**Status**: Active  
**Last Updated**: March 18, 2026  
**Next Review**: After remaining Tier 1 tasks completion  
**Maintainer**: Next assigned AI agent

**To Update This Document**:
1. Add new session to SESSIONS.md
2. Update completion percentages in tables
3. Modify success metrics if criteria change
4. Add new common issues as discovered
5. Increment version number if major changes

---

## Contact & Support

**Questions About This Plan**:
- Refer to KotStep_V3_Strategic_Plan.md for detailed breakdown
- Check SESSIONS.md for historical decisions
- Review attached skills for methodology

**Questions About Implementation**:
- Check GitHub issues and existing discussions
- Review commit history for context
- Test manually with Android device/emulator

---

**Last Updated**: March 18, 2026  
**Next Scheduled Review**: After Tier 1 completion



