package com.archivomedico.personal.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Cliente para el servicio de n8n
    private val n8nRetrofit = Retrofit.Builder()
        .baseUrl("https://primary-production-d6e1d.up.railway.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val n8nApiService: N8nApiService = n8nRetrofit.create(N8nApiService::class.java)

    // Cliente para el servicio de Google Places
    private val placesRetrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val placesApiService: PlacesApiService = placesRetrofit.create(PlacesApiService::class.java)
}