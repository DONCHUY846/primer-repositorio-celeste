package com.example.first_steps

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VentanaDatos : ComponentActivity(), CoroutineScope by MainScope() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ventanadatos)

        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val btnGet = findViewById<Button>(R.id.btnGet)
        val btnPost = findViewById<Button>(R.id.btnPost)

        btnGet.setOnClickListener {
            launch {
                try {
                    val lista = api.obtenerDatos()
                    if (lista.isNotEmpty()) {
                        val ultimo = lista.last()
                        tvResultado.text =
                            "Último Registro:\nID: ${ultimo.id}\nFecha: ${ultimo.fecha ?: "Sin fecha"}"
                    } else {
                        tvResultado.text = "No hay registros"
                    }
                } catch (e: Exception) {
                    tvResultado.text = "Error GET: ${e.message}"
                }
            }
        }

        btnPost.setOnClickListener {
            launch {
                try {

                    val nuevoDato = MiDato()
                    val respuesta = api.enviarDato(nuevoDato)

                    tvResultado.text =
                        "Registro Creado:\nID: ${respuesta.id}\nFecha: ${respuesta.fecha ?: "Generada por server"}"
                } catch (e: Exception) {
                    tvResultado.text = "Error POST: ${e.message}"
                }
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}