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

            if (nombre.isNullOrBlank() || posicion == null || numero.isNullOrBlank()) {
                val alerta = Alert(Alert.AlertType.WARNING)
                alerta.title = "Campos incompletos"
                alerta.headerText = null
                alerta.contentText = "Por favor complete todos los campos."
                alerta.showAndWait()
            } else {
                // Cerrar ventana actual
                stage.close()
                // Abrir simulador con los datos ingresados
                Simulador(nombre, posicion, numero).mostrar()
            }
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
