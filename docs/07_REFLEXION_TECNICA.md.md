# Reflexión Técnica Final

## Tabla de Cumplimiento - Criterios de Evaluación

| Criterio / Entregable | Estado | Detalle |
| :--- | :---: | :--- |
| **Proyecto base compila y ejecuta** | ✅ Completado | Verificado mediante `mvn clean test` sobre Java 21 y Surefire. |
| **Seis escenarios de línea base** | ✅ Completado | Diseñados e implementados 7 tests de caracterización con el patrón AAA. |
| **Mapa de responsabilidades** | ✅ Completado | Mapeadas las clases `ServicioReservas`, `Reserva` y `EstadoReserva`. |
| **Mínimo 5 problemas diagnosticados** | ✅ Completado | *Long Method*, *Feature Envy*, *Magic Numbers*, bajo encapsulamiento y alto acoplamiento. |
| **Matriz de riesgo** | ✅ Completado | Evaluado impacto de regresión y complejidad de refactorización por componente. |
| **Pruebas propuestas** | ✅ Completado | Red de seguridad construida previa a la refactorización. |
| **Plan priorizado** | ✅ Completado | Fase 1: aislamiento de cálculo; Fase 2: extracción de lógica y encapsulamiento. |
| **Commit Git del estado inicial** | ✅ Completado | Historial guardado y sincronizado en la rama `main` de GitHub. |
| **Reflexión técnica** | ✅ Completado | Desarrollada a continuación en este documento. |

---

## Análisis Técnico y Preguntas de Reflexión

El principal problema de diseño con mayor riesgo en el sistema legado era la concentración de responsabilidades en el método `procesar()` de `ServicioReservas.java`. Al acumular las reglas de negocio para calcular precios, la mutación directa del estado de la reserva y la notificación por correo electrónico, cualquier modificación introducía un alto riesgo de regresión no deseada.

Un aspecto aparentemente sencillo pero crítico de modificar son las constantes de descuentos (*Magic Numbers* como `0.85`). Sin una suite de pruebas, ajustar estos valores puede alterar drásticamente el comportamiento esperado para clientes VIP o tarifas base. Por ello, antes de realizar cualquier refactorización, resulta indispensable contar con pruebas unitarias de caracterización basadas en el patrón *Arrange-Act-Assert* (AAA). Estas pruebas actúan como una red de seguridad (*safety net*) que fija el comportamiento actual exacto sin importar cuán acoplado esté el código.

La primera responsabilidad a trasladar fue el cálculo del monto total mediante la técnica *Extract Method*. Mover este cálculo a un método privado (`calcularTotal`) permitió reducir la complejidad de `procesar()` manteniendo intacto su contrato público. La validez de esta decisión se defendió mediante la ejecución continua de `mvn clean test`, donde los 7 casos de prueba se mantuvieron en verde (`BUILD SUCCESS`), complementado con una prueba de regresión intencional que JUnit detectó correctamente (`expected: <34.0> but was: <32.0>`).

A diferencia de un cambio funcional —que añade o modifica características de negocio—, refactorizar preserva estrictamente el comportamiento externo mientras optimiza la legibilidad, cohesión y mantenibilidad de la arquitectura del software.

---

## Checklist de Verificación
- [x] Proyecto base compila y ejecuta.
- [x] Seis escenarios de línea base.
- [x] Mapa de responsabilidades.
- [x] Mínimo cinco problemas diagnosticados.
- [x] Matriz de riesgo.
- [x] Pruebas propuestas.
- [x] Plan priorizado.
- [x] Commit Git del estado inicial.
- [x] Reflexión técnica.