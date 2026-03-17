# KotStep V3 Strategic Implementation Plan

**Document Version**: 1.4  
**Last Updated**: March 18, 2026  
**Status**: Tier 1 in progress, label refactor implemented, V3 UI test harness compiled  
**Current Priority**: Run the new V3 instrumentation suite and finish Tier 1 accessibility hardening

---

## Executive Summary

This document tracks the prioritized improvement plan for KotStep V3. Execution has started. The first two Tier 1 tasks are complete:

- debug logging removed from `HorizontalStepItem.kt`
- state-aware accessibility semantics added to `StepIndicator.kt`

The remaining Tier 1 work is touch target validation and color contrast auditing.

**Library Version**: 3.1.0  
**Upcoming Feature**: Leading icons for step components  
**Current Phase**: Tier 1 execution in progress

---

## Tier 1: Performance & Accessibility

### COMPLETE: Remove Debug Logging
- **File**: `HorizontalStepItem.kt`
- **Change**: Removed the debug `Log.d()` call and its `LaunchedEffect`
- **Impact**: Removes unnecessary production overhead
- **Skill**: `compose-performance-audit`

### COMPLETE: Dynamic Accessibility Descriptions
- **File**: `StepIndicator.kt`
- **Change**: Added state-aware `contentDescription` and `stateDescription`
- **Impact**: Screen readers now receive useful step context instead of a generic label
- **Skill**: `android-accessibility`

### PENDING: Touch Target Size Validation
- **Target**: All clickable step surfaces
- **Requirement**: Minimum 48x48 dp
- **Priority**: High

### PENDING: Color Contrast Audit
- **Target**: Default step and line styles
- **Requirement**: WCAG AA for text and icon contrast
- **Priority**: High

---

## Tier 2: Testing Infrastructure

- Unit test setup for DSL and style logic
- Compose UI tests for layout and interactions
- Screenshot tests for regression coverage

Status: In progress, initial Compose UI coverage added and compiled

---

## Tier 3: Leading/Trailing Label Layout Refactor

- Add `leadingLabel` and `trailingLabel` support to the step model and DSL
- Keep the step spine aligned across all items while optional labels render around it
- Update documentation and samples

Status: Implemented on March 18, 2026 ahead of Tier 2 by direct user request

---

## Tier 4: Code Quality

- State hoisting review
- Modifier ordering review
- Unused import cleanup

Status: Pending

---

## Tier 5: Build Infrastructure

- Convention plugins
- Dependency/version catalog cleanup

Status: Optional and pending

---

## Execution Log

### Session 1: March 18, 2026

Completed:
1. Reviewed the project-local KotStep skills and planning files
2. Resolved documentation ambiguity by checking the live source files
3. Removed debug logging from `HorizontalStepItem.kt`
4. Added state-aware semantics to `StepIndicator.kt`
5. Verified with `.\gradlew.bat assembleDebug`
6. Updated the planning and session documents to match reality

Next session priority:
1. Touch target size audit
2. Color contrast audit
3. Tier 2 test setup

### Session 2: March 18, 2026

Completed:
1. Added `leadingIcon` to the `Step` model and all DSL builders
2. Rendered leading icons in both horizontal and vertical step items
3. Added a shared helper composable for consistent leading icon rendering
4. Updated samples to demonstrate the new API
5. Verified with `.\gradlew.bat assembleDebug`

Next session priority:
1. Finish Tier 1 touch target and contrast work
2. Add Tier 2 tests covering the new `leadingIcon` API

### Session 3: March 18, 2026

Completed:
1. Replaced the temporary `leadingIcon` API with `leadingLabel` and `trailingLabel`
2. Moved shared label-space reservation into the parent horizontal and vertical steppers
3. Updated step item layouts so the indicator/progress spine stays aligned across all steps
4. Updated local samples and affected app demo call sites
5. Verified with `.\gradlew.bat assembleDebug`

Next session priority:
1. Finish Tier 1 touch target and contrast work
2. Add Tier 2 tests for `leadingLabel` and `trailingLabel`

### Session 4: March 18, 2026

Completed:
1. Added Android test dependencies and stable V3 test tags
2. Wrote Compose UI tests for semantics, labels, and collapse alignment
3. Compiled the V3 Android test APK successfully
4. Kept the main debug build green

Next session priority:
1. Run `connectedAndroidTest` on an emulator or device
2. Expand Tier 2 coverage to more state and progress cases
3. Finish Tier 1 accessibility work

---

## Version Roadmap

| Version | Features | Status |
|---------|----------|--------|
| 3.1.0 | Current release | Complete |
| 3.2.0 | Leading icons + Tier 1 fixes | In progress |
| 3.2.1 | Test coverage improvements | Planned |
| 3.3.0 | Accessibility hardening follow-up | Planned |

---

## Handoff Notes

- Treat the source files as the source of truth if the planning docs drift again.
- Tier 1 is not complete until touch targets and contrast are explicitly verified.
- The temporary leading icon API was replaced by a broader leading/trailing label model.
- Future work should focus on executing and expanding the new V3 test suite, accessibility validation, and documentation refinement rather than another adjacent-content redesign.
- The next agent should update both this file and `SESSIONS.md` after any further work.

---

**Next Review**: After Tier 1 completion
