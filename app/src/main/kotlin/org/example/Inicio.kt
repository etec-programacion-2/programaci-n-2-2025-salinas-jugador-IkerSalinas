package org.example
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Inicio : Application() {
    override fun start(primaryStage: Stage) {
        // Crear un Label
        val label = Label("Bienvenido al Simulador")

        // Crear un botón
        val button = Button("Comenzar")
        button.setOnAction {
            val alert = Alert(Alert.AlertType.INFORMATION)
            alert.title = "Mensaje"
            alert.headerText = null
            alert.contentText = "Proximamente"
            alert.showAndWait()

        }

        // Layout centrado
        val root = VBox(20.0)
        root.alignment = Pos.CENTER
        root.children.addAll(label, button)

        // Crear escena
        val scene = Scene(root, 400.0, 300.0)
        primaryStage.title = "Simulador"
        primaryStage.scene = scene
        primaryStage.show()
    }
}
