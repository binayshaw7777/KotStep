# Pluggable Resolvers

KotStep SDUI provides a pluggable architecture to decouple JSON data payloads from client-specific assets and composable widgets.

---

## 1. Icon Resolver (`SduiIconResolver`)

Maps icon identifier strings from JSON payloads to native Compose Multiplatform `ImageVector` instances:

```kotlin
val customIconResolver = SduiIconResolver { iconName ->
    when (iconName) {
        "shopping_cart"  -> Icons.Default.ShoppingCart
        "local_shipping" -> Icons.Default.LocalShipping
        "payment"        -> Icons.Default.Payment
        "check"          -> Icons.Default.Check
        "lock"           -> Icons.Default.Lock
        else             -> null // Fallback to number indicator
    }
}

KotStepSdui(
    manager = manager,
    iconResolver = customIconResolver
)
```

---

## 2. Color Resolver (`SduiColorResolver`)

Parses hex color codes safely without runtime crashes:
- Supports `#RRGGBB`, `#AARRGGBB`, and `#RGB`.
- If an invalid hex string is supplied, it falls back to neutral `Color.DarkGray` without throwing exceptions.

```kotlin
val customColorResolver = SduiColorResolver { colorHex ->
    when (colorHex) {
        "brand_primary" -> Color(0xFF6200EE)
        "brand_accent"  -> Color(0xFF03DAC6)
        else            -> DefaultSduiColorResolver.resolve(colorHex)
    }
}
```

---

## 3. Custom Content Resolver (`SduiContentResolver`)

For steps with `indicator.type == "CUSTOM"`, render arbitrary Composables inside the step indicator slot:

```kotlin
val customContentResolver = SduiContentResolver { slotName, metadata ->
    when (slotName) {
        "pulsing_radar" -> {
            RadarPulseIndicator(pulseColor = Color.Cyan)
        }
        "user_avatar" -> {
            AsyncImage(
                model = metadata["avatarUrl"],
                contentDescription = null,
                modifier = Modifier.size(32.dp).clip(CircleShape)
            )
        }
        else -> null
    }
}

KotStepSdui(
    manager = manager,
    contentResolver = customContentResolver
)
```
