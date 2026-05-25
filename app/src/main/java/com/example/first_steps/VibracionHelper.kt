package com.example.first_steps

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibracionHelper(context: Context) {

    // Inicializar el servicio de vibración como lo hizo la maestra
    private val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun iniciarVibracion() {
        if (vibrator.hasVibrator()) {
            // Lógica para versiones modernas (API 26+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                // Lógica para versiones antiguas
                @Suppress("DEPRECATION")
                vibrator.vibrate(500)
            }
        }
    }
}