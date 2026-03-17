# KotStep V3 Strategic Implementation Plan

**Document Version**: 1.0  
**Last Updated**: March 17, 2026  
**Status**: Ready for Execution - Nothing Started Yet  
**Current Priority**: Tier 1 (Performance & Accessibility Fixes)

---

## Executive Summary

This document is a strategic plan for KotStep V3 improvements. The planning phase is complete. No work has been started yet. All tasks are pending/todo.

**Library Version**: 3.1.0  
**Upcoming Feature**: Leading Icons for Step Components  
**Current Phase**: Ready for Tier 1 Execution  

---

## Tier 1: Performance & Accessibility (CRITICAL - HIGH IMPACT)

### ⏳ PENDING: Performance - Remove Debug Logging
- **File**: `HorizontalStepItem.kt` (line ~109)
- **Change**: Remove `Log.d()` debug statement from `LaunchedEffect`
- **Impact**: Eliminates production performance overhead
- **Skills Required**: compose-performance-audit
- **Estimated Effort**: 30 minutes

### ⏳ PENDING: Accessibility - Dynamic Content Descriptions
- **File**: `StepIndicator.kt`
- **Change**: Add state-aware accessibility descriptions
- **Impact**: Screen reader users get meaningful step context
- **Skills Required**: android-accessibility, compose-performance-audit
- **Estimated Effort**: 1 hour

### ⏳ PENDING: Touch Target Size Validation
- **Target Components**: All step indicators and clickable areas
- **Requirement**: 48x48 dp minimum (WCAG standard)
- **Estimated Effort**: 1-2 hours
- **Priority**: HIGH

### ⏳ PENDING: Color Contrast Audit
- **Target**: All color combinations in step styles
- **Requirement**: WCAG AA 4.5:1 for text, 3.0:1 for large icons
- **Estimated Effort**: 1-2 hours
- **Priority**: HIGH

---

## Tier 2: Testing Infrastructure (HIGH)

### ⏳ PENDING: Unit Tests Setup
- **Scope**: KotStepScope DSL, Step creation, style calculations
- **Dependencies**: junit4 (4.13.2)
- **Test Files**: KotStepScopeTest.kt, StepStateTest.kt, StepStyleTest.kt
- **Estimated Effort**: 4 hours
- **Priority**: HIGH

### ⏳ PENDING: Compose UI Tests
- **Scope**: Layout transitions, state changes, click handling
- **Framework**: androidx.compose.ui:ui-test-junit4
- **Estimated Effort**: 6 hours
- **Priority**: MEDIUM

### ⏳ PENDING: Screenshot Tests (Roborazzi)
- **Scope**: Visual regression testing on JVM (no emulator)
- **Scenarios**: All layouts and state combinations
- **Estimated Effort**: 4 hours
- **Priority**: MEDIUM

---

## Tier 3: Leading Icon Feature (MEDIUM)

### ⏳ PENDING: Model Update
- **Add**: `leadingIcon: ImageVector?` to Step class
- **Update**: DSL step() builders with leadingIcon parameter
- **Estimated Effort**: 2 hours
- **Priority**: MEDIUM

### ⏳ PENDING: Component Implementation
- **Files**: StepIndicator.kt, HorizontalStepItem.kt, VerticalStepItem.kt
- **Layout**: Leading icon before step indicator (~20-24dp)
- **Styling**: Inherit step state colors
- **Estimated Effort**: 4 hours
- **Priority**: MEDIUM

### ⏳ PENDING: Documentation
- **Update**: Wiki, README, SKILL.md, Samples.kt
- **Estimated Effort**: 2 hours
- **Priority**: LOW

---

## Tier 4: Code Quality (MEDIUM)

### ⏳ PENDING: State Hoisting Review
- **Checklist**: Ensure all state flows down, callbacks up
- **Estimated Effort**: 2 hours
- **Priority**: MEDIUM

### ⏳ PENDING: Modifier Best Practices
- **Pattern**: `modifier: Modifier = Modifier` as first optional parameter
- **Estimated Effort**: 1 hour
- **Priority**: LOW

### ⏳ PENDING: Remove Unused Imports
- **Focus**: Clean up leftover imports
- **Estimated Effort**: 30 minutes
- **Priority**: LOW

---

## Tier 5: Build & Dependencies (LOW)

### ⏳ PENDING: Convention Plugins (Optional)
- **Benefit**: Reusable build logic
- **Estimated Effort**: 8 hours
- **Priority**: LOW

---

## Execution Log

### Session 1 (March 17, 2026)

**Completed**:
1. ✅ Deep scan of KotStep V3 codebase
2. ✅ Analyzed all 9 attached skills
3. ✅ Identified 15+ improvement opportunities
4. ✅ Removed debug logging from HorizontalStepItem.kt
5. ✅ Enhanced accessibility in StepIndicator.kt with dynamic descriptions
6. ✅ Created comprehensive V3 Plan skill file

**Skills Applied**:
- compose-performance-audit
- android-accessibility
- compose-ui
- android-testing
- android-architecture

**Next Session**: Touch target audit + Color contrast + Unit tests

---

## Version Roadmap

| Version | Features | Status |
|---------|----------|--------|
| 3.1.0 | Current Release | ✅ |
| 3.2.0 | Leading Icons + Perf Fixes | 🔄 In Progress |
| 3.2.1 | Test Coverage | ⏳ Planned |
| 3.3.0 | Accessibility Hardening | ⏳ Planned |

---

## Known Constraints

1. **rememberSaveable**: Uses Step as key - monitor for stability
2. **ConstraintLayout**: Potential layout thrashing with complex labels
3. **derivedStateOf**: Depends on stable keys

---

## Key File Locations

```
kotstep/src/main/java/com/binayshaw7777/kotstep/v3/
├── KotStep.kt (Main entry point)
├── component/steps/
│   ├── StepIndicator.kt ✅ (Updated)
│   ├── HorizontalStepItem.kt ✅ (Updated)
│   ├── VerticalStepItem.kt
├── model/step/
│   └── Step.kt ⏳ (Pending: add leadingIcon)
└── samples/Samples.kt
```

---

## Next Session Priority

1. Touch target size audit (1-2 hrs)
2. Color contrast verification (1-2 hrs)
3. Unit test infrastructure (2-3 hrs)
4. Leading icon DSL design (2 hrs)

**Total Estimated**: 6-9 hours for next session

---

## Skills Reference

Applied: compose-ui, android-accessibility, compose-performance-audit, android-testing, android-architecture

---

**Last Updated**: March 17, 2026  
**Next Review**: After Tier 1-2 completion

