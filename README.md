# ISO2-2026-GrupoB04-Testing-P1

Ingeniería del Software II – Segundo trabajo (Parte Testing) – **Problema 1: aparcamiento**

| Paso | Qué se hace | Pareja |
|------|-------------|--------|
| Paso 1 | Implementación del programa (rama `develop`) | Pareja 1 |
| Paso 2 | Diseño de casos de prueba (ejercicio teórico) | Pareja 2 |
| Paso 3 | Implementación de las pruebas en JUnit (rama `hotfix`) | Pareja 3 |

## Enunciado

Método que, recibiendo la fecha/hora de entrada y de salida de un aparcamiento, devuelve el coste total
de la estancia. Si la salida es anterior a la entrada, o alguna fecha es nula, se lanza una excepción.

- Los primeros 30 minutos son gratuitos.
- Después se cobra 0,05 € el minuto.
- Tope máximo diario de 20 €.
- Interfaz en línea de comandos, sin acoplar el dominio con la interfaz.

## Estructura

```
es.uclm.esi.iso2.aparcamiento
├── Main                          → monta las piezas y arranca
├── dominio
│   ├── Estancia                  → entrada/salida; valida en el constructor; getMinutos()
│   ├── CalculadoraTarifa         → interfaz: calcularCoste(Estancia)
│   └── TarifaEstandar            → reglas de negocio (30 min gratis, 0,05 €/min, tope 20 €/día)
├── excepciones
│   ├── EstanciaInvalidaException → base
│   ├── FechaNulaException        → entrada o salida nula
│   ├── SalidaAnteriorException   → salida anterior a la entrada
│   └── FormatoFechaInvalidoException → texto de teclado no válido
└── ui
    ├── LectorConsola             → lee de teclado y parsea fechas (dd/MM/yyyy HH:mm)
    ├── PantallaConsola           → escribe en pantalla
    └── ControladorAparcamiento   → une UI y dominio
```

El paquete `dominio` no depende de `ui`. `LectorConsola` y `PantallaConsola` reciben el
`InputStream`/`PrintStream` por constructor para poder probarlos sin teclado ni consola reales.

## Decisiones de diseño

1. **Validación en el constructor de `Estancia`.** Si existe un objeto `Estancia`, es válido. Atributos privados y `final`.
2. **Fechas nulas:** `FechaNulaException`. **Salida anterior a entrada:** `SalidaAnteriorException`. Ambas heredan de `EstanciaInvalidaException` (excepciones comprobadas).
3. **Entrada y salida iguales** es válido y cuesta 0 €.
4. **Redondeo del tiempo:** cualquier fracción de minuto empezada cuenta como minuto completo (30 min 1 s → 31 min).
5. **Los 30 minutos gratis** se aplican una sola vez, al principio de la estancia.
6. **Tope diario:** 20 € por cada periodo de 24 h contado desde la entrada (no por día natural).
   - Cada periodo completo de 24 h cuesta 20 €.
   - Los minutos restantes se cobran a 0,05 €/min con tope de 20 €.
   - En el primer periodo se descuentan los 30 minutos gratis.
7. **Dinero con `BigDecimal`** (2 decimales) para evitar errores de redondeo de `double`.
8. **Fechas sin zona horaria (`LocalDateTime`):** los cambios de hora (horario de verano) no se tienen en cuenta.
9. **Formato de fecha por teclado:** `dd/MM/yyyy HH:mm`, validación estricta (por ejemplo, 30/02 se rechaza).

### Fórmula

```
min = minutos de la estancia (redondeo hacia arriba)
si min <= 30 → 0 €
dias  = min div 1440
resto = min mod 1440
si dias == 0 → cobrables = resto - 30
si no        → cobrables = resto
costeResto = min(cobrables × 0,05 ; 20)
coste = dias × 20 + costeResto
```

### Ejemplos

| Duración | Coste |
|----------|-------|
| 0 min | 0,00 € |
| 30 min | 0,00 € |
| 31 min | 0,05 € |
| 429 min | 19,95 € |
| 430 min | 20,00 € |
| 1440 min (24 h) | 20,00 € |
| 1441 min | 20,05 € |
| 2880 min (48 h) | 40,00 € |

## Cómo compilar y ejecutar

```bash
mvn clean package
java -jar target/ISO2-2026-GrupoB04-Testing-P1-1.0.0.jar
```

Ejemplo de uso:

```
Fecha/hora de entrada: 24/11/2026 10:00
Fecha/hora de salida:  24/11/2026 11:15
Duración de la estancia: 75 minutos
Coste total: 2,25 EUR
```

## Pruebas (Paso 3)

Las pruebas van en `src/test/java` (rama `hotfix`). El `pom.xml` ya incluye JUnit 5, Surefire y Jacoco:

```bash
mvn clean test                 # ejecuta las pruebas; informe Jacoco en target/site/jacoco/index.html
mvn surefire-report:report     # informe Surefire en target/site/surefire-report.html
```
