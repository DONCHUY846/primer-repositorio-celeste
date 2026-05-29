package com.example.first_steps

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class SegundaVentana : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana2)

        val vibrador = VibracionHelper(this)


        val botonVibrar = findViewById<Button>(R.id.button2)

        botonVibrar.setOnClickListener {
            vibrador.iniciarVibracion()
            Toast.makeText(this, "Vibración iniciada", Toast.LENGTH_SHORT).show()
        }

        val botonReproducir = findViewById<Button>(R.id.boton_reproducir)
        botonReproducir.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, R.raw.we_are_charlie_kirk_song)
            mediaPlayer?.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}