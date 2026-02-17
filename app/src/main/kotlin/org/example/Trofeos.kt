package org.example

import kotlin.random.Random

object Trofeos {

    fun generarTrofeos(
        nombre: String,
        posicion: String,
        goles: Int,
        asistencias: Int,
        equipo: String,
        liga: String,
        valorMercado: Double
    ): List<String> {
        val trofeos = mutableListOf<String>()

        // -----------------------------
        // Campeonatos de liga local
        // -----------------------------
        val chanceLiga = when (equipo) {
            in Equipos.gigantes -> 0.6
            in Equipos.grandes -> 0.4
            in Equipos.normales -> 0.2
            else -> 0.1
        }
        if (Random.nextDouble() < chanceLiga) {
            trofeos.add("🏆 Campeón de $liga con $equipo")
        }

        // -----------------------------
        // Competiciones internacionales de clubes
        // -----------------------------
        if (liga in listOf("Premier League", "La Liga", "Serie A", "Bundesliga", "Ligue 1")) {
            val chanceChampions = if (equipo in Equipos.gigantes) 0.25 else if (equipo in Equipos.grandes) 0.15 else 0.05
            if (Random.nextDouble() < chanceChampions) {
                trofeos.add("🌍 Champions League con $equipo")
            } else if (Random.nextDouble() < 0.1) {
                trofeos.add("🏆 Europa League con $equipo")
            } else if (Random.nextDouble() < 0.05) {
                trofeos.add("⚽ Conference League con $equipo")
            }
        } else if (liga in listOf("Liga Argentina", "Brasileirao", "Liga Roth")) {
            val chanceLibertadores = if (equipo in Equipos.gigantes) 0.25 else 0.1
            if (Random.nextDouble() < chanceLibertadores) {
                trofeos.add("🏆 Copa Libertadores con $equipo")
            } else if (Random.nextDouble() < 0.1) {
                trofeos.add("🏆 Copa Sudamericana con $equipo")
            }
        }

        // -----------------------------
        // Selección nacional
        // -----------------------------
        val chanceSeleccion = Random.nextDouble()
        when {
            chanceSeleccion < 0.05 -> trofeos.add("🌍 Mundial de selecciones")
            chanceSeleccion < 0.10 -> trofeos.add("🏆 Eurocopa / Copa América")
        }

        // -----------------------------
        // Premios individuales
        // -----------------------------
        if (posicion == "Delantero" && goles >= 30 && Random.nextDouble() < 0.5) {
            trofeos.add("🥇 Bota de Oro (máximo goleador)")
        }

        val impacto = goles + asistencias
        if (impacto > 30 && valorMercado > 80_000_000 && Random.nextDouble() < 0.15) {
            trofeos.add("🏆 Balón de Oro")
        }

        return if (trofeos.isEmpty()) listOf("⚽ Sin títulos esta temporada") else trofeos
    }
}
