package com.example.first_steps.presentation
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.first_steps.R
import com.example.first_steps.presentation.SegundaVentana
class SegundaVentana: ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.ventana2)
    }
}