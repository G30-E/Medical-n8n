package com.archivomedico.personal.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface N8nApiService {
    @POST("webhook-test/citas")
    fun sendNote(@Body note: NoteData): Call<Void>
}

data class NoteData(
    val title: String,
    val body: String,
    val email: String,
    val timestamp: String,
    val patientName: String // Campo añadido
)
