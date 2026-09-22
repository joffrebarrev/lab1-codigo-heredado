# lab1-codigo-heredado

Proyecto de laboratorio para el curso **Diseño de Software (UCOM0310)** — Universidad Espíritu Santo (UEES), PEL 4, 2026.

Sistema heredado de gestión de reservas (`ServicioReservas`) usado como caso de estudio progresivo para practicar **diagnóstico de código heredado**, **refactorización protegida por pruebas** y **patrones de diseño**, siguiendo el ciclo:

```
DIAGNOSTICAR → PROBAR → REFACTORIZAR → VERIFICAR → EVIDENCIAR
```

---

## 📌 Estado actual

| | |
|---|---|
| **Build** | ✅ `mvn clean test` → `BUILD SUCCESS` |
| **Pruebas** | 7/7 en verde (JUnit 5) |
| **Java** | 21 |
| **Build tool** | Maven |
| **Última actividad** | Ae5 — Refactorización respaldada por pruebas unitarias |

---

## 🗂️ Estructura del proyecto

```
lab1-codigo-heredado/
├── pom.xml
├── README.md
├── docs/
│   └── (informes técnicos y reflexiones de cada actividad)
├── src/
│   ├── main/java/edu/uees/refactor/
│   │   ├── domain/
│   │   │   ├── Reserva.java          # Entidad del dominio, encapsula su estado (confirmar())
│   │   │   ├── EstadoReserva.java    # Enum: PENDIENTE, CONFIRMADA
│   │   │   └── Monto.java            # Value Object (record) — importe con validación e inmutabilidad
│   │   ├── service/
│   │   │   ├── ServicioReservas.java # Orquestador: valida, confirma, delega notificación y cálculo
│   │   │   └── NotificadorEmail.java # Responsabilidad extraída (Extract Class) — envío de notificaciones
│   │   └── app/
│   │       └── Main.java             # Punto de entrada de demostración end-to-end
│   └── test/java/edu/uees/refactor/service/
│       └── ServicioReservasTest.java # Suite de 7 pruebas de caracterización (red de seguridad)
└── target/                           # Generado por Maven (no versionado)
```

---

## ⚙️ Cómo ejecutar el proyecto

### Requisitos
- JDK 21
- Maven 3.9+

### Clonar el repositorio
```bash
git clone https://github.com/joffrebarrev/lab1-codigo-heredado.git
cd lab1-codigo-heredado
```

### Compilar y ejecutar las pruebas
```bash
mvn clean test
```
Salida esperada:
```
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Ejecutar la demo end-to-end (clase Main)
```bash
mvn compile exec:java -Dexec.mainClass="edu.uees.refactor.app.Main"
```
Salida esperada:
```
Guardando reserva R-001
Correo enviado a ana@uees.edu.ec
Estado: CONFIRMADA
Total: 34.0
```

---

## 🧪 Suite de pruebas — red de seguridad (JUnit 5)

`ServicioReservasTest` concentra 7 pruebas de caracterización que protegen cada regla de negocio y caso límite del sistema. Ninguna refactorización se da por completa sin que las 7 permanezcan en verde.

| # | Prueba | Escenario | Protege |
|---|---|---|---|
| 1 | `normalActualmenteRetornaCuarenta()` | Reserva NORMAL, 5h anticipación | Tarifa base ($40.0) |
| 2 | `vipActualmenteRetornaTreintaYCuatro()` | Reserva VIP, 5h anticipación | Descuento VIP (15%) |
| 3 | `correoInvalidoNoProcesaReserva()` | Email sin `@` | Rechazo de correo mal formado |
| 4 | `periodoConFinAnteriorNoProcesa()` | Fecha fin anterior a inicio | Períodos de fecha inconsistentes |
| 5 | `dosHorasExactasPermitenProcesar()` | Reserva NORMAL, 2h anticipación | Caso límite (frontera permitida) |
| 6 | `unaHoraNoPermiteProcesar()` | Reserva NORMAL, 1h anticipación | Caso límite (frontera rechazada) |
| 7 | `reservaNulaRetornaCero()` | Objeto `Reserva == null` | Manejo defensivo ante nulos |

---

## 🔄 Historial de refactorizaciones

El proyecto documenta un proceso incremental y trazable, con un commit atómico por cada cambio, siguiendo siempre el ciclo **Prueba Verde → Cambio pequeño → Prueba Verde → Commit**.

### Actividad 1 — Diagnóstico de código heredado (línea base)
Evaluación estructural del código heredado: mapa de responsabilidades (SRP), matriz de *code smells*, matriz de riesgo y suite inicial de pruebas de caracterización.

### Actividad 2 — Laboratorio: construcción de la red de seguridad con JUnit 5
- **Extract Method**: se extrajo el cálculo del total a `calcularTotal(Reserva r)`, dejando `procesar()` como un orquestador legible.
- **Demostración de regresión**: se introdujo un error intencional en el descuento VIP (0.85 → 0.80); la suite lo detectó de inmediato (`AssertionFailedError: expected <34.0> but was <32.0>`), confirmando la efectividad de la red de seguridad.
- El cambio se revirtió con `git restore` y la suite volvió a `BUILD SUCCESS`.

### Ae5 — Refactorización respaldada por pruebas unitarias
Tres refactorizaciones adicionales, cada una verificada por la suite completa antes de comprometerse a Git:

1. **Extract Class** — se creó `NotificadorEmail` para separar la responsabilidad de notificación de `ServicioReservas` (Principio de Responsabilidad Única).
2. **Introduce Value Object** — se creó el `record Monto`, inmutable y con validación explícita, para eliminar el *Primitive Obsession* del cálculo financiero.
3. **Guard Clauses** — las validaciones anidadas de `procesar()` se extrajeron a `esReservaInvalida(...)`, reduciendo la complejidad ciclomática mediante retornos tempranos.

### Commits principales

| Hash | Mensaje |
|---|---|
| `8079ecd` | `test: caracterizar comportamiento heredado de reservas` |
| `8f64710` | `refactor: extraer calculo de total a metodo privado` |
| `d2a2e55` | `refactor: completar refactorizacion protegida y demostracion de regresion` |
| `af92829` | `docs: agregar informe completo de la Actividad 2 con preguntas de reflexion` |
| `0c952bd` | `docs: actualizar reflexion tecnica con tabla de cumplimiento y checklist` |
| `81e4c01` | `refactor: aplicar Extract Class separando NotificadorEmail de ServicioReservas` |
| `5a818cc` | `refactor: introducir Value Object Monto para encapsular calculos financieros` |
| `d3e4237` | `refactor: simplificar condicionales anidadas aplicando Guard Clauses` |

Historial completo:
```bash
git log --oneline
```

---

## 🧩 Diseño del dominio

```
Reserva              — entidad con id, correo, inicio, fin, tipo, estado
  └─ confirmar()      — único punto autorizado para transicionar el estado a CONFIRMADA

EstadoReserva         — enum: PENDIENTE | CONFIRMADA

Monto (record)        — value object inmutable
  ├─ valor: double     — validado (no negativo) en el constructor compacto
  └─ aplicarDescuento(double porcentaje): Monto

ServicioReservas       — orquestador
  ├─ procesar(Reserva, int horasAnticipacion): double
  ├─ esReservaInvalida(Reserva, int): boolean   (Guard Clauses)
  ├─ calcularTotal(Reserva): double             (usa Monto)
  └─ notificador: NotificadorEmail              (inyectado por constructor)

NotificadorEmail
  └─ enviarConfirmacion(Reserva)
```

---

## 📄 Documentación adicional

Los informes técnicos completos de cada actividad (diagnóstico, evidencia de ejecución, tablas comparativas antes/después, preguntas de reflexión y declaración de uso de IA) se encuentran en `docs/` y en las entregas de Blackboard correspondientes:

- Lab 1 — Diagnóstico de código heredado
- Actividad 2 — Laboratorio: construcción de la red de seguridad con JUnit 5
- Ae5 — Refactorización respaldada por pruebas unitarias

---

## 👤 Autor

**Joffre Barre Velíz**
Universidad Espíritu Santo (UEES) — Ingeniería en Computación
Diseño de Software (UCOM0310), PEL 4 - 2026

---

## 🤖 Declaración de uso de IA

Se utilizó asistencia de IA (Claude, Anthropic) como herramienta de apoyo para la redacción y el formato de los informes técnicos y de este README. El diagnóstico del código heredado, el diseño e implementación de las refactorizaciones, la escritura y ejecución de las pruebas, y los commits de Git fueron realizados por el estudiante.
