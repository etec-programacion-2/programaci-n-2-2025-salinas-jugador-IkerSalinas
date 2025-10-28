package org.example

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.layout.VBox
import javafx.stage.Stage

class Opciones {

    fun mostrar() {
        val stage = Stage()

        val check1 = CheckBox("Mute Musica")


        val botonCerrar = Button("Cerrar")
        botonCerrar.setOnAction {
            stage.close()
        }

        val root = VBox(15.0, check1, botonCerrar)
        root.alignment = Pos.CENTER

        val scene = Scene(root, 300.0, 250.0)
        stage.title = "Opciones"
        stage.scene = scene
        stage.show()
    }
}
