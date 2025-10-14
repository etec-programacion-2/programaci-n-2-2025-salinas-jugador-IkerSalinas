package org.example

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.random.Random

class Simulador(
    private var nombre: String,
    private var posicion: String,
    private var numero: String
) {
    private var edad = Random.nextInt(16, 19)
    private var temporadaActual = 2024
    private var goles = 0
    private var asistencias = 0
    private var amarillas = 0
    private var rojas = 0
    private var partidos = 0
    private var puesto = "Reserva"
    private var valorMercado = 1_000_000.0
    private var equipo = "Club Atlético Inicial"
    private var liga = "Liga Argentina"



    // Historial de trofeos
    private val historialTrofeos = mutableListOf<String>()

    // 📌 Estadísticas de carrera acumuladas
    private var golesTotales = 0
    private var asistenciasTotales = 0
    private var amarillasTotales = 0
    private var rojasTotales = 0
    private var partidosTotales = 0
    private val equiposJugados = mutableListOf<String>()
    private val trofeosTotales = mutableListOf<String>()
    private var lesionesTotales = 0
    private var partidosPerdidosPorLesion = 0
    private var enPrestamo = false
    private var temporadasRestantesPrestamo = 0
    private var equipoPrestamo: String? = null
    private var equipoOriginal: String? = null
    fun mostrar() {
        val stage = Stage()

        // Labels
        val labelNombre = javafx.scene.control.Label("Jugador: $nombre")
        val labelPosicion = javafx.scene.control.Label("Posición: $posicion")
        val labelNumero = javafx.scene.control.Label("Número: $numero")
        val labelEdad = javafx.scene.control.Label("Edad: $edad")
        val labelTemporada = javafx.scene.control.Label("Temporada: ${temporadaActual}/${temporadaActual + 1}")
        val labelStats = javafx.scene.control.Label("Estadísticas: Goles $goles, Asistencias $asistencias, Amarillas $amarillas, Rojas $rojas, Partidos $partidos")
        val labelEquipo = javafx.scene.control.Label("Equipo: $equipo | Liga: $liga")
        val labelPuesto = javafx.scene.control.Label("Puesto en el equipo: $puesto")
        val labelValor = javafx.scene.control.Label("Valor de mercado: €${String.format("%,.0f", valorMercado)}")

        // Botón próxima temporada
        val boton = Button("Próxima temporada")
        boton.setOnAction {
            val evento = Eventos.generarEvento(equipo, posicion)
            if (evento.prestamoEquipo != null && !enPrestamo) {
                equipoOriginal = equipo  // 🔹 Guardamos el equipo que te presta
                enPrestamo = true
                temporadasRestantesPrestamo = evento.temporadasPrestamo
                equipoPrestamo = evento.prestamoEquipo
                equipo = "${evento.prestamoEquipo} (préstamo)"
            }

            if (evento.lesionOcurrida) {
                lesionesTotales++
                partidosPerdidosPorLesion += evento.lesionPartidosPerdidos
                goles = (goles * 0.8).toInt()
                asistencias = (asistencias * 0.8).toInt()
                partidos = (partidos - evento.lesionPartidosPerdidos).coerceAtLeast(0)
            }

            if (evento.prestamoEquipo != null && !enPrestamo) {
                enPrestamo = true
                temporadasRestantesPrestamo = evento.temporadasPrestamo
                equipoPrestamo = evento.prestamoEquipo
                equipo = "${evento.prestamoEquipo} (préstamo)"
            }

            simularTemporada()
            // 📌 Acumular estadísticas de carrera
            golesTotales += goles
            asistenciasTotales += asistencias
            amarillasTotales += amarillas
            rojasTotales += rojas
            partidosTotales += partidos
            equiposJugados.add(equipo) // cuenta una temporada en ese club


            // Generar trofeos
            val trofeos = Trofeos.generarTrofeos(nombre, posicion, goles, asistencias, equipo, liga, valorMercado)
            historialTrofeos.add("📅 Temporada ${temporadaActual - 1}/${temporadaActual}:")
            historialTrofeos.addAll(trofeos)
            trofeosTotales.addAll(trofeos)

            // Actualizar labels
            labelEdad.text = "Edad: $edad"
            labelTemporada.text = "Temporada: ${temporadaActual}/${temporadaActual + 1}"
            labelStats.text = "Estadísticas: Goles $goles, Asistencias $asistencias, Amarillas $amarillas, Rojas $rojas, Partidos $partidos"
            labelEquipo.text = "Equipo: $equipo | Liga: $liga"
            labelPuesto.text = "Puesto en el equipo: $puesto"
            labelValor.text = "Valor de mercado: €${String.format("%,.0f", valorMercado)}"

            // Mostrar trofeos de la temporada
            val alertaTrofeos = Alert(Alert.AlertType.INFORMATION)
            alertaTrofeos.title = "Trofeos de la temporada"
            alertaTrofeos.headerText = "Temporada ${temporadaActual - 1}/${temporadaActual} finalizada"
            alertaTrofeos.contentText = if (trofeos.isEmpty()) "No ganaste trofeos" else trofeos.joinToString("\n")
            alertaTrofeos.showAndWait()

            // 📌 Revisar retiro manual y automático
            if (edad >= 30 && root.children.none { it is Button && it.text == "Retiro" }) {
                val botonRetiro = Button("Retiro")
                botonRetiro.setOnAction {
                    val retiro = Retiro(
                        nombre, posicion, numero,
                        golesTotales, asistenciasTotales, amarillasTotales, rojasTotales,
                        partidosTotales, trofeosTotales, equiposJugados,
                        lesionesTotales, partidosPerdidosPorLesion
                    )

                    retiro.mostrar()
                    stage.close()
                }
                root.children.add(botonRetiro)
            }
            if (edad >= 48) {
                val retiro = Retiro(
                    nombre, posicion, numero,
                    golesTotales, asistenciasTotales, amarillasTotales, rojasTotales,
                    partidosTotales, trofeosTotales, equiposJugados,
                    lesionesTotales, partidosPerdidosPorLesion
                )

                retiro.mostrar()
                stage.close()
            }
        }

        // Botón cambiar de equipo
        val botonEquipo = Button("Cambiar de equipo")
        botonEquipo.setOnAction {
            val ofertas = generarOfertas()

            if (ofertas.isEmpty()) {
                val alerta = Alert(Alert.AlertType.WARNING)
                alerta.title = "Sin ofertas"
                alerta.headerText = null
                alerta.contentText = "No has recibido ofertas esta temporada."
                alerta.showAndWait()
                return@setOnAction
            }

            val dialog = javafx.scene.control.ChoiceDialog(ofertas[0], ofertas)
            dialog.title = "Ofertas de equipos"
            dialog.headerText = "Elige tu nuevo equipo"
            dialog.contentText = "Ofertas disponibles:"

            val resultado = dialog.showAndWait()
            if (resultado.isPresent) {
                val nuevoEquipo = resultado.get()
                equipo = nuevoEquipo

                // Buscar en qué liga está
                for ((ligaNombre, categorias) in Ligas.ligas) {
                    for ((_, listaEquipos) in categorias) {
                        if (nuevoEquipo in listaEquipos) {
                            liga = ligaNombre
                        }
                    }
                }

                // Actualizar labels
                labelEquipo.text = "Equipo: $equipo | Liga: $liga"
            }
        }

        // Botón historial de trofeos
        val botonHistorial = Button("Ver historial de trofeos")
        botonHistorial.setOnAction {
            val alertaHistorial = Alert(Alert.AlertType.INFORMATION)
            alertaHistorial.title = "Historial de trofeos"
            alertaHistorial.headerText = "Todos los títulos ganados por $nombre"
            alertaHistorial.contentText =
                if (historialTrofeos.isEmpty()) "Aún no has ganado trofeos"
                else historialTrofeos.joinToString("\n")
            alertaHistorial.showAndWait()
        }

        // Layout
        root = VBox(
            12.0,
            labelNombre, labelPosicion, labelNumero,
            labelEdad, labelTemporada,
            labelStats, labelEquipo, labelPuesto, labelValor,
            boton, botonEquipo, botonHistorial
        )
        root.alignment = Pos.CENTER

        val scene = Scene(root, 520.0, 550.0)
        stage.title = "Simulación de carrera"
        stage.scene = scene
        stage.show()
    }

    // -------------------------------
    // Lógica de simulación de temporada
    // -------------------------------
    private fun simularTemporada() {
        temporadaActual += 1
        edad += 1

        // Stats base según posición
        goles = when (posicion) {
            "Delantero" -> Random.nextInt(0, 50)
            "Medio" -> Random.nextInt(0, 30)
            "Defensa" -> Random.nextInt(0, 10)
            "Arquero" -> if (Random.nextInt(0, 100) < 2) 1 else 0
            else -> 0
        }
        asistencias = when (posicion) {
            "Delantero" -> Random.nextInt(0, 30)
            "Medio" -> Random.nextInt(0, 50)
            "Defensa" -> Random.nextInt(0, 20)
            "Arquero" -> Random.nextInt(0, 10)
            else -> 0
        }
        amarillas = Random.nextInt(0, 15)
        rojas = if (amarillas > 4 && Random.nextDouble() < 0.3) 1 else 0
        partidos = Random.nextInt(0, 60)

        puesto = when (Random.nextInt(0, 2)) {
            0 -> "Titular"
            1 -> "Suplente"
            else -> "Reserva"
        }

        // Ajuste de valor de mercado
        val rendimiento = goles * 5 + asistencias * 3 - amarillas - rojas * 2
        valorMercado += rendimiento * 100_000
        if (puesto == "Titular") valorMercado *= 1.1
        if (edad > 30) valorMercado *= 0.9
        if (valorMercado < 100_000) valorMercado = 100_000.0
        if (enPrestamo) {
            temporadasRestantesPrestamo--
            if (temporadasRestantesPrestamo <= 0) {
                val comprado = Eventos.evaluarCompraPrestamo(goles + asistencias, true)
                if (comprado) {
                    equipo = equipoPrestamo!!.replace(" (préstamo)", "")
                    val alerta = Alert(Alert.AlertType.INFORMATION)
                    alerta.title = "Traspaso definitivo"
                    alerta.headerText = "¡Compra definitiva!"
                    alerta.contentText = "El club $equipo te compró tras el préstamo."
                    alerta.showAndWait()
                } else {
                    equipo = equipoOriginal ?: "Club Atlético Inicial"
                    val alerta = Alert(Alert.AlertType.INFORMATION)
                    alerta.title = "Fin del préstamo"
                    alerta.headerText = "Regresaste a tu club original"
                    alerta.contentText = "Volviste a jugar con $equipo."
                    alerta.showAndWait()
                }
                enPrestamo = false
                equipoPrestamo = null
            }
        }
    }

    // -------------------------------
    // Ofertas de equipos según rendimiento
    // -------------------------------
    private fun generarOfertas(): List<String> {
        val ofertas = mutableListOf<String>()
        val rendimiento = goles + asistencias - rojas

        when {
            rendimiento > 50 -> ofertas.addAll(Equipos.gigantes.shuffled().take(2))
            rendimiento > 30 -> ofertas.addAll(Equipos.grandes.shuffled().take(2))
            rendimiento > 15 -> ofertas.addAll(Equipos.normales.shuffled().take(2))
            else -> ofertas.addAll(Equipos.mediocres.shuffled().take(2))
        }
        return ofertas
    }

    companion object {
        private lateinit var root: VBox
    }
}
