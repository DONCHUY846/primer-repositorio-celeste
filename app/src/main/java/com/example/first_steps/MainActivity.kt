package com.example.first_steps

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            Toast.makeText(this, "Hola desde Ventana 1", Toast.LENGTH_SHORT).show()
        }


        val botonNavegar = findViewById<Button>(R.id.button_ir_ventana2)
        botonNavegar.setOnClickListener {
            val intent = Intent(this, SegundaVentana::class.java)
            startActivity(intent)
        }
    }
}