package com.example.first_steps

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// Estructura de datos para JSON
data class MiDato(
    val id: Int? = null,
    // Usamos String para la fecha para que sea más fácil de manejar desde el JSON
    @SerializedName("created_at") // Así vinculamos "created_at" del JSON con "fecha" en Kotlin
    val fecha: String? = null
)

interface ApiService {
    @GET("posts") // Usando posts como ejemplo de jsonplaceholder
    suspend fun obtenerDatos(): List<MiDato>

    @POST("posts")
    suspend fun enviarDato(@Body dato: MiDato): MiDato
}