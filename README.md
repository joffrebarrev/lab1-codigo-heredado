lab1-codigo-heredado

Proyecto de laboratorio para el curso Diseño de Software (UCOM0310) — Universidad Espíritu Santo (UEES), PEL 4, 2026.

Sistema heredado de gestión de reservas (ServicioReservas) usado como caso de estudio progresivo para practicar diagnóstico de código heredado, refactorización protegida por pruebas y patrones de diseño, siguiendo el ciclo:

DIAGNOSTICAR → PROBAR → REFACTORIZAR → VERIFICAR → EVIDENCIAR

📌 Información General
Institución: Universidad Especialidades Espíritu Santo (UEES)[cite: 4, 5]

Carrera: Ingeniería en Computación

Materia: Diseño de Software | UCOM0310[cite: 5]

Estudiante: Joffre Barre Velíz[cite: 4]

Semana: Semana 6 · PEL 4 – 2026[cite: 5]

Repositorio: https://github.com/joffrebarrev/lab1-codigo-heredado[cite: 4, 5]

🎯 Propósito del Proyecto
Demostrar la capacidad de mejorar el diseño de un código heredado aplicando técnicas avanzadas de refactorización, mientras una suite de pruebas unitarias con JUnit 5 actúa como red de seguridad para garantizar que la conducta externa del sistema permanezca inalterada (Ciclo Seguro: PRUEBA VERDE → CAMBIO PEQUEÑO → PRUEBA VERDE → COMMIT)[cite: 5].

🛠️ Tecnologías Utilizadas
Lenguaje: Java 21[cite: 3]

Gestor de Dependencias y Construcción: Apache Maven[cite: 3, 5]

Testing Framework: JUnit 5 (JUnit Platform / Surefire)[cite: 3, 5]

Control de Versiones: Git & GitHub[cite: 4, 5]

🚀 Requisitos e Instrucciones de Ejecución
Prerrequisitos
Java Development Kit (JDK) 21 o superior instalado[cite: 3].

Apache Maven 3.8+ configurado en las variables de entorno[cite: 3, 5].

Git instalado[cite: 4, 5].

Pasos para Clonar y Ejecutar
Clonar el repositorio:

Bash
git clone https://github.com/joffrebarrev/lab1-codigo-heredado.git
cd lab1-codigo-heredado
Ejecutar la suite completa de pruebas unitarias:

Bash
mvn clean test
Resultado esperado:

Plaintext
[INFO] Running edu.uees.refactor.service.ServicioReservasTest
...
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
🧩 Refactorizaciones Aplicadas
Se aplicaron tres refactorizaciones de mayor alcance justificadas a partir del diagnóstico del código heredado[cite: 4, 5]:

1. Extract Class (NotificadorEmail)
Problema: La clase ServicioReservas violaba el Principio de Responsabilidad Única (SRP) al manejar la orquestación del negocio y la lógica de notificaciones por correo electrónico a consola[cite: 4].

Técnica: Extract Class[cite: 5]. Se extrajo la responsabilidad de envío de mensajes a la nueva clase NotificadorEmail e inyectada como dependencia[cite: 3].

Commit: 81e4c01 — refactor: extraer responsabilidad de notificación a NotificadorEmail[cite: 3, 5]

2. Introduce Value Object (Monto)
Problema: Obsesión por Primitivos (Primitive Obsession) al manejar valores monetarios y porcentajes con variables tipo double sueltas y desprotegidas[cite: 3, 5].

Técnica: Introduce Value Object[cite: 5]. Se creó el record Monto(double valor) encapsulando las operaciones financieras, inmutabilidad y validaciones de valores no negativos[cite: 3].

Commit: 5a818cc — refactor: introducir Value Object Monto para encapsular importes[cite: 3, 5]

3. Guard Clauses / Decompose Conditional (esReservaInvalida)
Problema: Condicionales anidados complejos (Deeply Nested Conditionals) que oscurecían el camino principal del método procesar[cite: 4, 5].

Técnica: Guard Clauses / Decompose Conditional[cite: 5]. Se extrajeron las validaciones de fallo temprano al método privado esReservaInvalida(Reserva r, int horasAnticipacion)[cite: 3].

Commit: d3e4237 — refactor: simplificar condicionales anidadas aplicando Guard Clauses[cite: 3, 5]

📊 Matriz Comparativa (Antes vs. Después)
Dimensión[cite: 5]	Estado Inicial (Antes)[cite: 5]	Estado Final (Después)[cite: 5]
Responsabilidades[cite: 5]	Mezcladas en ServicioReservas (validación, cobro, estado, notificación)[cite: 4].	Segregadas: ServicioReservas (orquestador), NotificadorEmail (comunicación), Monto (financiero)[cite: 3].
Cohesión[cite: 5]	Baja cohesión[cite: 4].	Alta cohesión bajo el Principio SRP[cite: 3, 4].
Acoplamiento[cite: 5]	Directo a primitivos y salidas por consola[cite: 4].	Débil gracias a Inyección de Dependencias y Value Objects[cite: 3].
Datos del Dominio[cite: 5]	Primitivos double sueltos[cite: 3, 4].	Encapsulados e inmutables mediante el record Monto[cite: 3].
Condicionales[cite: 5]	Bloques if anidados profundos[cite: 4].	Aplanados usando cláusulas de guarda (Guard Clauses)[cite: 3, 5].
Pruebas[cite: 5]	Vulnerable a regresiones silenciosas[cite: 3].	Suite de 7 pruebas protegiendo el 100% de los escenarios[cite: 3, 4].
Git[cite: 5]	Cambios masivos sin rastreo atómico[cite: 4].	Historial con commits incrementales en ciclo seguro[cite: 3, 5].
📜 Historial de Commits Incrementales
Plaintext
d3e4237 (HEAD -> main, origin/main) refactor: simplificar condicionales anidadas aplicando Guard Clauses
5a818cc refactor: introducir Value Object Monto para encapsular importes
81e4c01 refactor: extraer responsabilidad de notificación a NotificadorEmail
8f64710 refactor: extraer calculo de total a metodo privado
8079ecd test: caracterizar comportamiento heredado de reservas
🛡️ Preguntas de Defensa (Resumen Técico)
¿Qué comportamiento protegiste antes de refactorizar?
Las reglas de cálculo de tarifa base (40.0), descuento VIP (34.0), invalidación por formato de correo/fechas inconsistentes y la actualización de estado a CONFIRMADA[cite: 3].

¿Por qué estas refactorizaciones?
Atacan directamente la baja cohesión (Extract Class), la obsesión por primitivos (Value Object) y la alta complejidad ciclomática (Guard Clauses)[cite: 3, 4, 5].

¿Qué prueba detectó una regresión?
vipActualmenteRetornaTreintaYCuatro(). Al alterar intencionalmente el cálculo de descuento, detuvo la compilación con BUILD FAILURE[cite: 3, 4].

¿Qué cambió en el diseño y qué permaneció igual?
Cambió la estructura interna y abstracción de datos; permaneció inalterado el contrato público y las salidas del sistema[cite: 3, 4].

📂 Estructura del Proyecto
Plaintext
lab1-codigo-heredado/
├── docs/
│   └── Informe_Tecnico_AE5_Joffre_Barre_con_Evidencias.docx
├── src/
│   ├── main/java/edu/uees/refactor/
│   │   ├── domain/
│   │   │   ├── Monto.java
│   │   │   └── Reserva.java
│   │   └── service/
│   │       ├── NotificadorEmail.java
│   │       └── ServicioReservas.java
│   └── test/java/edu/uees/refactor/service/
│       └── ServicioReservasTest.java
├── pom.xml
└── README.md
🤖 Declaración de Uso de Inteligencia Artificial
Se utilizó asistencia de IA como apoyo interactivo para el diagnóstico de Code Smells, estructuración de Value Objects en Java 21 y redacción del reporte técnico bajo los criterios de la rúbrica[cite: 5]. La ejecución técnica, implementación de código, pruebas y comandos Git fueron ejecutados y validados de manera individual por el estudiante[cite: 4, 5].
