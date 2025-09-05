package org.example

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlin.random.Random
import kotlin.math.max

class Simulador(
    private val nombre: String,
    private val posicion: String,
    private val numero: String
) {
    private var edad = Random.nextInt(16, 19) // entre 16 y 18
    private var inicioTemporada = 2024 // primer año de temporada

    // estadísticas de la temporada actual
    private var goles = 0
    private var asistencias = 0
    private var amarillas = 0
    private var rojas = 0
    private var partidos = 0
    private var puesto = "Titular"

    // equipo
    private var equipo = "Equipo Genérico"
    private var liga = "Liga Local"

    // historial acumulado
    private var golesTotales = 0
    private var asistenciasTotales = 0
    private var amarillasTotales = 0
    private var rojasTotales = 0
    private var partidosTotales = 0

    // valor calculado
    private var valorMercado = 0

    fun mostrar() {
        val stage = Stage()

        // Generar estadísticas iniciales
        generarEstadisticas()
        calcularValor()

        // Labels
        val labelNombre = Label("Nombre: $nombre")
        val labelPosicion = Label("Posición: $posicion")
        val labelNumero = Label("Número: $numero")
        val labelValor = Label("Valor de mercado: €$valorMercado")
        val labelEdad = Label("Edad: $edad años")
        val labelTemporada = Label("Temporada: ${formatoTemporada()}")
        val labelStats = Label(statsTexto())
        val labelEquipo = Label("Equipo: $equipo | Liga: $liga")
        val labelPuesto = Label("Puesto: $puesto")

        // Botón próxima temporada
        val boton = Button("Próxima temporada")
        boton.setOnAction {
            // acumular estadísticas
            golesTotales += goles
            asistenciasTotales += asistencias
            amarillasTotales += amarillas
            rojasTotales += rojas
            partidosTotales += partidos

            // avanzar temporada
            inicioTemporada += 1
            edad += 1
            generarEstadisticas()
            calcularValor()

            // actualizar labels
            labelValor.text = "Valor de mercado: €$valorMercado"
            labelEdad.text = "Edad: $edad años"
            labelTemporada.text = "Temporada: ${formatoTemporada()}"
            labelStats.text = statsTexto()
            labelPuesto.text = "Puesto: $puesto"


        }

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
        partidos = Random.nextInt(10, 70)

        when (posicion.lowercase()) {
            "delantero" -> {
                goles = Random.nextInt(0, 70)
                asistencias = Random.nextInt(0, 30)
                amarillas = Random.nextInt(0, 10)
                rojas = Random.nextInt(0, 4)
            }
            "medio" -> {
                goles = Random.nextInt(0, 30)
                asistencias = Random.nextInt(0, 50)
                amarillas = Random.nextInt(0, 10)
                rojas = Random.nextInt(0, 4)
            }
            "defensa" -> {
                goles = Random.nextInt(0, 20)
                asistencias = Random.nextInt(0,20)
                amarillas = Random.nextInt(0,15)
                rojas = Random.nextInt(0, 6)
            }
            "arquero" -> {
                goles = if (Random.nextInt(0, 100) < 2) 1 else 0
                asistencias = Random.nextInt(0, 3)
                amarillas = Random.nextInt(0, 4)
                rojas = Random.nextInt(0, 2)
            }
        }

        puesto = when (Random.nextInt(0, 100)) {
            in 0..70 -> "Titular"
            in 71..95 -> "Suplente"
            else -> "Reserva"
        }
    }

    private fun calcularValor() {
        // base depende de partidos jugados
        var nuevoValor = partidos * 50_000

        // goles y asistencias suman bastante
        nuevoValor += goles * 150_000
        nuevoValor += asistencias * 100_000

        // disciplina resta
        nuevoValor -= amarillas * 20_000
        nuevoValor -= rojas * 50_000

        // puesto
        nuevoValor += when (puesto) {
            "Titular" -> 500_000
            "Suplente" -> 200_000
            else -> 50_000
        }

        // ajustar por edad
        nuevoValor = when (edad) {
            in 18..23 -> (nuevoValor * 1.2).toInt()
            in 24..28 -> (nuevoValor * 1.5).toInt()
            in 29..32 -> (nuevoValor * 1.1).toInt()
            else -> (nuevoValor * 0.7).toInt()
        }

        // asegurar mínimo
        valorMercado = max(nuevoValor, 100_000)
    }

    private fun statsTexto(): String {
        return "Goles: $goles | Asistencias: $asistencias | Amarillas: $amarillas | Rojas: $rojas | Partidos: $partidos"
    }

    private fun formatoTemporada(): String {
        val siguiente = inicioTemporada + 1
        return "$inicioTemporada/$siguiente"
    }
}

