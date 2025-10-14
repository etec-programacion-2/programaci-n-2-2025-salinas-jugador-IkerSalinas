package org.example

import javafx.scene.control.Alert
import kotlin.random.Random

object Eventos {

    data class ResultadoEvento(
        val lesionPartidosPerdidos: Int = 0,
        val lesionOcurrida: Boolean = false,
        val prestamoEquipo: String? = null,
        val temporadasPrestamo: Int = 0,
        val conOpcionCompra: Boolean = false,
        val comprado: Boolean = false
    )

    fun generarEvento(equipoActual: String, posicion: String): ResultadoEvento {
        val prob = Random.nextDouble()

        // ⚠️ 5% probabilidad de lesión
        if (prob < 0.15) {
            val partidosPerdidos = Random.nextInt(2, 20)
            val alerta = Alert(Alert.AlertType.WARNING)
            alerta.title = "Lesión"
            alerta.headerText = "¡$equipoActual informa una lesión!"
            alerta.contentText = "Te perdiste $partidosPerdidos partidos por lesión."
            alerta.showAndWait()
            return ResultadoEvento(
                lesionOcurrida = true,
                lesionPartidosPerdidos = partidosPerdidos
            )
        }

        // ⚽ 3% probabilidad de préstamo
        if (prob in 0.10..0.20) {
            val esConOpcion = Random.nextDouble() < 0.4 // 40% son con opción de compra
            val duracion = Random.nextInt(2, 5) // 1 a 3 temporadas
            val equipoPrestamo = obtenerEquipoPrestamo(equipoActual)
            val alerta = Alert(Alert.AlertType.INFORMATION)
            val durpres = duracion - 1
            alerta.title = "Préstamo"
            alerta.headerText = if (esConOpcion)
                "Fuiste cedido a préstamo (con opción de compra)"
            else
                "Fuiste cedido a préstamo"
            alerta.contentText = "Nuevo equipo: $equipoPrestamo por $durpres temporada(s)."
            alerta.showAndWait()
            return ResultadoEvento(
                prestamoEquipo = equipoPrestamo,
                temporadasPrestamo = duracion,
                conOpcionCompra = esConOpcion
            )
        }

        // 🟢 Sin evento
        return ResultadoEvento()
    }

    private fun obtenerEquipoPrestamo(equipoActual: String): String {
        // Buscar categoría del equipo actual
        val categoriaActual = when {
            equipoActual in Equipos.gigantes -> Equipos.gigantes
            equipoActual in Equipos.grandes -> Equipos.grandes
            equipoActual in Equipos.normales -> Equipos.normales
            else -> Equipos.mediocres
        }

        // Equipos posibles para préstamo (igual o más baja)
        val posiblesEquipos = when (categoriaActual) {
            Equipos.gigantes -> Equipos.grandes + Equipos.normales + Equipos.mediocres
            Equipos.grandes -> Equipos.normales + Equipos.mediocres
            Equipos.normales -> Equipos.mediocres
            else -> Equipos.mediocres
        }.filterNot { it == equipoActual }

        return posiblesEquipos.randomOrNull() ?: "Equipo Desconocido"
    }

    fun evaluarCompraPrestamo(rendimiento: Int, conOpcion: Boolean): Boolean {
        // Si es préstamo con opción y jugó bien, pueden comprarlo
        if (!conOpcion) return false
        return rendimiento > 15 && Random.nextDouble() < 0.5
    }
}
