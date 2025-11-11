Iker Salinas


# ⚽ Simulador de Carrera de Futbolista (Kotlin + JavaFX)

Proyecto realizado en **Kotlin** utilizando **JavaFX** como entorno gráfico.  
El objetivo es simular la carrera completa de un jugador de fútbol: desde su debut hasta el retiro, pasando por temporadas, traspasos, lesiones, rendimiento y trofeos.

---

## 🚀 Tecnologías utilizadas

- **Lenguaje:** Kotlin  v1.9.24
- **Interfaz gráfica:** JavaFX  v21.0.2
- **Entorno de desarrollo:** IntelliJ IDEA  
- **Sistema de compilación:** Gradle v8.7  
- **Arquitectura modular:** cada pantalla o sistema en su propio archivo `.kt`

---

## 🧱 Estructura del proyecto

```
app/
 ├── src/main/kotlin/org/example/
 │    ├── App.kt
 │    ├── Inicio.kt
 │    ├── Jugador.kt
 │    ├── Simulador.kt
 │    ├── Equipos.kt
 │    ├── Ligas.kt
 │    ├── Eventos.kt
 │    ├── Trofeo.kt
 │    └── Retiro.kt
 └── build.gradle.kts
```

---

## 📋 Descripción de cada módulo

### 🏠 **App.kt**
- Contiene la función `main`.
- Inicia la aplicación y abre la pantalla principal (`Inicio.kt`).

---

### 🧍‍♂️ **Inicio.kt**
- Muestra una ventana con un **botón** para comenzar la simulación.
- Centra los elementos en pantalla con un `VBox`.

---

### 👤 **Jugador.kt**
- Ventana para **crear un jugador**.  
- Contiene:
  - Campo de texto para ingresar el **nombre**.
  - ComboBox para elegir la **posición** (Delantero, Medio, Defensa o Arquero).
  - Campo para elegir el **número de camiseta**.
  - Botón **"Empezar carrera"** → abre el simulador.  
- Si el nombre ingresado es **"S.Valla"**, muestra el mensaje “No podés jugar por muerto”.

---

### 🏟️ **Simulador.kt**
- Núcleo de la simulación de carrera.  
- Muestra:
  - Nombre, edad, posición, equipo actual y liga.  
  - Estadísticas por temporada (variables según la posición).  
  - Valor de mercado calculado dinámicamente.  
  - Botón **"Próxima temporada"** para avanzar en el tiempo.  
  - Botón **"Cambiar equipo"** (para elegir un nuevo club según rendimiento).  
  - Botón **"Retiro"** cuando llega a los 38 años o **retiro automático** a los 48.

#### 📊 Estadísticas por posición:
- **Delantero:** Goles, asistencias, tarjetas, partidos.  
- **Mediocampista:** Pases, pases clave, asistencias, goles.  
- **Defensa:** Robos, goles evitados, goles, tarjetas.  
- **Arquero:** Paradas, goles en contra, porterías a cero, tarjetas.

#### 💰 Valor de mercado:
- Calculado con fórmulas distintas por posición.  
- Temporada espectacular ≈ rendimiento > 100 pts.  
- Se actualiza con base en estadísticas y edad.

#### 💸 Traspasos:
- Ofertas generadas por la función `generarOfertas()` según rendimiento:
  - +100 pts → clubes gigantes.
  - 60–100 pts → clubes grandes.
  - 30–60 pts → clubes normales.
  - <30 pts → clubes mediocres.

---

### ⚙️ **Eventos.kt**
- Sistema de **eventos aleatorios** con baja probabilidad:
  - **Lesiones:** reducen rendimiento y te hacen perder partidos.
  - **Préstamos:** te envían a otro club (1 a 3 temporadas), con o sin opción de compra.
  - Los equipos de préstamo siempre son de igual o menor categoría.
- Las lesiones y partidos perdidos se registran y se muestran al final de la carrera.

---

### 🏆 **Trofeo.kt**
- Sistema de trofeos y títulos por temporada:
  - Campeonatos locales, copas internacionales y premios individuales.
  - **Balón de Oro / Bota de Oro** si el jugador tiene gran rendimiento.
  - Trofeos con selección nacional.
  - Clasificación a torneos internacionales según liga (Champions, Libertadores, etc.).
- Los trofeos ganados se agrupan al final, mostrando cuántas veces se repitió cada título.

---

### 🏳️‍🌈 **Equipos.kt**
- Contiene las listas de equipos divididos por nivel:
  - **Gigantes:** ej. Real Madrid, Bayern, PSG.
  - **Grandes:** ej. Inter, Arsenal, Atlético.
  - **Normales:** ej. Porto, Lazio, Betis.
  - **Mediocres:** ej. Godoy Cruz, Celta, Lecce.

---

### 🌍 **Ligas.kt**
- Define las ligas donde juegan los equipos:
  - **Liga Argentina**, **Bundesliga**, **Premier League**, **Serie A**, **Ligue 1**, **La Liga**, **Brasileirão**.
- Cada liga tiene 2 equipos por categoría.

---

### 🩺 **Retiro.kt**
- Pantalla final de la carrera del jugador.  
- Muestra:
  - Estadísticas totales de carrera (goles, asistencias, tarjetas, partidos).  
  - Total de **lesiones sufridas** y **partidos perdidos**.  
  - Listado de **trofeos ganados** agrupados con repeticiones (`x2`, `x3`, etc.).  
  - Equipos en los que jugó (incluyendo préstamos).  
- Se activa automáticamente al llegar a los **48 años** o manualmente a los **38**.

---

## 🎲 Mecánicas internas

- 🔢 **Edad inicial:** 16–18 años.  
- 📈 **Evolución:** cada temporada aumenta la edad, recalcula estadísticas y valor de mercado.  
- 💥 **Lesiones:** bajan rendimiento de la temporada actual.  
- 💸 **Préstamos:** pueden incluir opción de compra, que depende del rendimiento.  
- 🧠 **Retiro:** muestra todo el historial del jugador.

---

## 💻 Cómo ejecutar el proyecto

1. Abrí el proyecto en **IntelliJ IDEA**.  
2. Asegurate de tener el plugin de **JavaFX configurado**.  
3. En `build.gradle.kts` agregá las dependencias:

   ```kotlin
   dependencies {
       implementation("org.openjfx:javafx-controls:21")
       implementation("org.openjfx:javafx-fxml:21")
   }
   ```

4. Ejecutá la función `main` desde `App.kt`.

---

## 🧮 Escala de rendimiento (0–150 pts)

| Rango | Descripción | Categoría de ofertas |
|--------|--------------|---------------------|
| 100–150 | 🔥 Temporada espectacular | Gigantes |
| 60–100 | 💪 Muy buena | Grandes |
| 30–60 | ⚙️ Aceptable | Normales |
| 0–30 | 😬 Floja | Mediocres |

---
