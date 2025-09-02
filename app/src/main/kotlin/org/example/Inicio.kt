package org.example

import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Inicio : Application() {
    override fun start(primaryStage: Stage) {
        val label = Label("Bienvenido")

        val button = Button("Ir a Jugador")
        button.setOnAction {
            // Cierra la ventana actual
            primaryStage.close()
            // Abre la nueva ventana de Jugador
            Jugador().mostrar()
        }

        val root = VBox(20.0, label, button)
        root.alignment = Pos.CENTER

        val scene = Scene(root, 400.0, 300.0)
        primaryStage.title = "Ventana Inicio"
        primaryStage.scene = scene
        primaryStage.show()
    }
}
