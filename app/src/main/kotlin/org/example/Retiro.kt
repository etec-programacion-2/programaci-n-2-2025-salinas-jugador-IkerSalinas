package org.example

import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Retiro(
    private val nombre: String,
    private val posicion: String,
    private val numero: String,
    private val golesTotales: Int,
    private val asistenciasTotales: Int,
    private val amarillasTotales: Int,
    private val rojasTotales: Int,
    private val partidosTotales: Int,
    private val trofeos: List<String>,
    private val equipos: List<String>
) {
    fun mostrar() {
        val stage = Stage()
        stage.title = "Retiro de $nombre"

        val labelTitulo = Label("🏆 Carrera de $nombre finalizada")
        val labelPosicion = Label("Posición: $posicion | Camiseta: $numero")
        val labelEstadisticas = Label(
            """
            Estadísticas de carrera:
            Goles: $golesTotales
            Asistencias: $asistenciasTotales
            Partidos jugados: $partidosTotales
            Amarillas: $amarillasTotales
            Rojas: $rojasTotales
            """.trimIndent()
        )

        // 📌 Agrupar trofeos por nombre
        val trofeosAgrupados = trofeos.groupingBy { it }.eachCount().map { (trofeo, cantidad) ->
            if (cantidad > 1) "$trofeo x$cantidad" else trofeo
        }

        val labelTrofeos = Label(
            "Trofeos ganados:\n" +
                    if (trofeosAgrupados.isEmpty()) "Ninguno"
                    else trofeosAgrupados.joinToString("\n")
        )

        // 📌 Agrupar equipos por repeticiones (temporadas jugadas en ese club)
        val equiposAgrupados = equipos.groupingBy { it }.eachCount().map { (equipo, cantidad) ->
            if (cantidad > 1) "$equipo x${cantidad} temporadas" else "$equipo x1 temporada"
        }

        val labelEquipos = Label(
            "Equipos en los que jugó:\n" +
                    if (equiposAgrupados.isEmpty()) "Ninguno"
                    else equiposAgrupados.joinToString("\n")
        )

        val layout = VBox(10.0, labelTitulo, labelPosicion, labelEstadisticas, labelTrofeos, labelEquipos)
        layout.spacing = 12.0

        val scene = Scene(layout, 400.0, 520.0)
        stage.scene = scene
        stage.show()

        val alerta = Alert(Alert.AlertType.INFORMATION)
        alerta.title = "Retiro"
        alerta.headerText = "El jugador $nombre se ha retirado"
        alerta.contentText = "Gracias por jugar ⚽"
        alerta.showAndWait()
    }
}
