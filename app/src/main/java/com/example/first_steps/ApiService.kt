package com.example.first_steps

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



data class MiDato(
    val id: Int? = null,
    @SerializedName("created_at")
    val fecha: String? = null
)

interface ApiService {
    @GET("posts")
    suspend fun obtenerDatos(): List<MiDato>

    @POST("posts")
    suspend fun enviarDato(@Body dato: MiDato): MiDato
}