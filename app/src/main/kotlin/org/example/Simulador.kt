package org.example

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.random.Random

class Simulador(
    private val nombre: String,
    private val posicion: String,
    private val numero: String
) {
    private var valorMercado = Random.nextInt(100_000, 10_000_000) // valor inicial
    private var edad = Random.nextInt(16, 19) // entre 16 y 18
    private var temporada = 1

    // estadísticas de la temporada
    private var goles = 0
    private var asistencias = 0
    private var amarillas = 0
    private var rojas = 0
    private var partidos = 0

    // equipo
    private var equipo = "Equipo Genérico"
    private var liga = "Liga Local"
    private var puesto = "Titular"

    fun mostrar() {
        val stage = Stage()

        // Generar estadísticas según posición
        generarEstadisticas()

        // Labels con info
        val labelNombre = Label("Nombre: $nombre")
        val labelPosicion = Label("Posición: $posicion")
        val labelNumero = Label("Número: $numero")
        val labelValor = Label("Valor de mercado: €$valorMercado")
        val labelEdad = Label("Edad: $edad años")
        val labelTemporada = Label("Temporada: $temporada")
        val labelStats = Label("Goles: $goles | Asistencias: $asistencias | Amarillas: $amarillas | Rojas: $rojas | Partidos: $partidos")
        val labelEquipo = Label("Equipo: $equipo | Liga: $liga")
        val labelPuesto = Label("Puesto: $puesto")

        // Botón para próxima temporada
        val boton = Button("Próxima temporada")
        boton.setOnAction {
            val alerta = Alert(Alert.AlertType.INFORMATION)
            alerta.title = "Simulación"
            alerta.headerText = null
            alerta.contentText = "Próximamente..."
            alerta.showAndWait()
        }

        // Layout
        val root = VBox(
            12.0,
            labelNombre, labelPosicion, labelNumero,
            labelValor, labelEdad, labelTemporada,
            labelStats, labelEquipo, labelPuesto,
            boton
        )
        root.alignment = Pos.CENTER

        val scene = Scene(root, 500.0, 420.0)
        stage.title = "Simulador de Carrera"
        stage.scene = scene
        stage.show()
    }

    private fun generarEstadisticas() {
        partidos = Random.nextInt(10, 80) // número de partidos jugados

        when (posicion.lowercase()) {
            "delantero" -> {
                goles = Random.nextInt(0, 70)
                asistencias = Random.nextInt(0, 30)
                amarillas = Random.nextInt(0, 10)
                rojas = Random.nextInt(0, 5)
            }
            "medio" -> {
                goles = Random.nextInt(0, 30)
                asistencias = Random.nextInt(0, 70)
                amarillas = Random.nextInt(0, 10)
                rojas = Random.nextInt(0, 5)
            }
            "defensa" -> {
                goles = Random.nextInt(0, 10)
                asistencias = Random.nextInt(0, 10)
                amarillas = Random.nextInt(0, 15)
                rojas = Random.nextInt(0, 8)
            }
            "arquero" -> {
                goles = if (Random.nextInt(0, 100) < 2) 1 else 0 // muy baja probabilidad de gol
                asistencias = Random.nextInt(0, 3)
                amarillas = Random.nextInt(0, 4)
                rojas = Random.nextInt(0, 2)
            }
        }

        // Elegir aleatoriamente puesto en el equipo
        puesto = when (Random.nextInt(0, 100)) {
            in 0..60 -> "Titular"
            in 61..95 -> "Suplente"
            else -> "Reserva"
        }
    }
}
