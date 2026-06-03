package com.example.first_steps

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class SegundaVentana : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var sensorHelper: SensorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventana2)
        val txtLectura = findViewById<TextView>(R.id.textLecturaSensor)
        val vibrador = VibracionHelper(this)

        sensorHelper = SensorHelper(this) { x, y, z ->
            txtLectura.text = "X: %.2f\nY: %.2f\nZ: %.2f".format(x, y, z)

            if (Math.abs(x) > 12 || Math.abs(y) > 12 || Math.abs(z) > 12) {
                vibrador.iniciarVibracion()
            }
        }

        val botonVibrar = findViewById<Button>(R.id.button2)
        botonVibrar.setOnClickListener {
            vibrador.iniciarVibracion()
            Toast.makeText(this, "Vibración manual", Toast.LENGTH_SHORT).show()
        }

        val botonReproducir = findViewById<Button>(R.id.boton_reproducir)
        botonReproducir.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, R.raw.we_are_charlie_kirk_song)
            mediaPlayer?.start()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorHelper.start()
    }

    override fun onPause() {
        super.onPause()
        sensorHelper.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
