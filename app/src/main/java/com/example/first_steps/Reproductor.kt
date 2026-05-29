package com.example.first_steps

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity

class ReproductorActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayer = MediaPlayer.create(this, R.raw.we_are_charlie_kirk_song)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}