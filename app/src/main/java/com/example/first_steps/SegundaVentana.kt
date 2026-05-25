package com.example.first_steps

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class SegundaVentana : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana2)

        val botonAlerta = findViewById<Button>(R.id.button2)
        botonAlerta.setOnClickListener {
            Toast.makeText(this, "Estas en la segunda ventana", Toast.LENGTH_SHORT).show()
        }
    }
}