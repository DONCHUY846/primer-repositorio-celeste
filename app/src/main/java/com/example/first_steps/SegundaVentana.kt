package com.example.first_steps

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class SegundaVentana : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana2)

        val vibrador = VibracionHelper(this)


        val botonVibrar = findViewById<Button>(R.id.button2)

        // 3. Activar la vibración al hacer clic
        botonVibrar.setOnClickListener {
            vibrador.iniciarVibracion()
            Toast.makeText(this, "Vibración iniciada", Toast.LENGTH_SHORT).show()
        }
    }
}