# About KotStep

KotStep is open-source software maintained by [Binay Shaw](https://github.com/binayshaw7777).

---

## Migration from V2 to V3

V2 was an Android-only library dependent on `constraintlayout-compose`. 
V3 is built entirely on pure Compose Multiplatform primitives for cross-platform support.

| Feature | V2 (Legacy) | V3 (Current) & SDUI |
|---|---|---|
| **Platforms** | Android only | Android, iOS, Desktop, Web |
| **API Style** | Sealed class factories (`tabHorizontal`, etc.) | Declarative `KotStep` DSL + `KotStepSdui` |
| **Dynamic Updates**| Rebuild entire stepper | Reactive `SduiStateManager` + Mutations |
| **Progress Line** | Step-by-step jump | Smooth sub-pixel animated float (`0f..Nf`) |
| **Labels** | Single label text | Leading & trailing composable label slots |
| **Server-Driven** | Not supported | Full JSON schema, mutations & rollback |

---

## Contributing

Contributions, bug reports, and suggestions are welcome!
- **Issues**: [github.com/binayshaw7777/KotStep/issues](https://github.com/binayshaw7777/KotStep/issues)
- **Repository**: [github.com/binayshaw7777/KotStep](https://github.com/binayshaw7777/KotStep)

---

## License

```
Copyright 2024 Binay Shaw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
