package org.example

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Jugador {
    fun mostrar() {
        val stage = Stage()

        // Label y campo de texto para el nombre
        val labelNombre = Label("Ingrese Nombre del jugador:")
        val inputNombre = TextField()

        // Label y ComboBox para la posición
        val labelPosicion = Label("Elije posición:")
        val comboPosicion = ComboBox<String>()
        comboPosicion.items.addAll("Delantero", "Medio", "Defensa", "Arquero")

        // Label y campo de texto para el número de camiseta
        val labelNumero = Label("Elija número de camiseta:")
        val inputNumero = TextField()

        // Botón
        val boton = Button("Empezar carrera")
        boton.setOnAction {
            val nombre = inputNombre.text
            val posicion = comboPosicion.value
            val numero = inputNumero.text

            val alerta = Alert(Alert.AlertType.INFORMATION)
            alerta.title = "Jugador creado"
            alerta.headerText = null

            // Verificamos que haya datos
            if (nombre.isNullOrBlank() || posicion == null || numero.isNullOrBlank()) {
                alerta.contentText = "Por favor complete todos los campos."
            } else {
                alerta.contentText = "Jugador: $nombre\nPosición: $posicion\nNúmero: $numero\n\nPróximamente..."
            }

            alerta.showAndWait()
        }

        // Layout
        val root = VBox(15.0,
            labelNombre, inputNombre,
            labelPosicion, comboPosicion,
            labelNumero, inputNumero,
            boton
        )
        root.alignment = Pos.CENTER

        val scene = Scene(root, 400.0, 400.0)
        stage.title = "Jugador"
        stage.scene = scene
        stage.show()
    }
}
