package com.archivomedico.personal.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {

    @GET("maps/api/place/nearbysearch/json")
    fun findNearbyHospitals(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String
    ): Call<PlacesResponse>
}

data class PlacesResponse(val results: List<Place>)
data class Place(
    val name: String,
    val vicinity: String,
    val geometry: Geometry
)
data class Geometry(val location: PlaceLocation)
data class PlaceLocation(val lat: Double, val lng: Double)