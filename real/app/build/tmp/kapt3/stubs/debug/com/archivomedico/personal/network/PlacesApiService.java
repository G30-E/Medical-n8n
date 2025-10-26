package com.archivomedico.personal.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J@\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\u0006H\'\u00a8\u0006\f"}, d2 = {"Lcom/archivomedico/personal/network/PlacesApiService;", "", "findNearbyHospitals", "Lretrofit2/Call;", "Lcom/archivomedico/personal/network/PlacesResponse;", "location", "", "radius", "", "type", "keyword", "apiKey", "app_debug"})
public abstract interface PlacesApiService {
    
    @retrofit2.http.GET(value = "maps/api/place/nearbysearch/json")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.archivomedico.personal.network.PlacesResponse> findNearbyHospitals(@retrofit2.http.Query(value = "location")
    @org.jetbrains.annotations.NotNull()
    java.lang.String location, @retrofit2.http.Query(value = "radius")
    int radius, @retrofit2.http.Query(value = "type")
    @org.jetbrains.annotations.NotNull()
    java.lang.String type, @retrofit2.http.Query(value = "keyword")
    @org.jetbrains.annotations.NotNull()
    java.lang.String keyword, @retrofit2.http.Query(value = "key")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey);
}