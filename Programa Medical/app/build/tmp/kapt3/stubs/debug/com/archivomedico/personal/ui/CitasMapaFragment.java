package com.archivomedico.personal.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0013H\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0002J$\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0013H\u0016J\u0010\u0010$\u001a\u00020\u00132\u0006\u0010%\u001a\u00020\u000fH\u0016J\u0010\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)H\u0016J-\u0010*\u001a\u00020\u00132\u0006\u0010+\u001a\u00020\u00062\u000e\u0010,\u001a\n\u0012\u0006\b\u0001\u0012\u00020.0-2\u0006\u0010/\u001a\u000200H\u0016\u00a2\u0006\u0002\u00101J\u001a\u00102\u001a\u00020\u00132\u0006\u00103\u001a\u00020\u001c2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/archivomedico/personal/ui/CitasMapaFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "Lcom/google/android/gms/maps/GoogleMap$OnMarkerClickListener;", "()V", "LOCATION_PERMISSION_REQUEST", "", "_binding", "Lcom/archivomedico/personal/databinding/FragmentCitasMapaBinding;", "binding", "getBinding", "()Lcom/archivomedico/personal/databinding/FragmentCitasMapaBinding;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "placesClient", "Lcom/google/android/libraries/places/api/net/PlacesClient;", "buscarLugarPorTipo", "", "tipo", "Lcom/google/android/libraries/places/api/model/Place$Type;", "buscarLugaresCercanos", "ubicacion", "Lcom/google/android/gms/maps/model/LatLng;", "checkLocationPermissionAndEnable", "enableUserLocation", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onMapReady", "googleMap", "onMarkerClick", "", "marker", "Lcom/google/android/gms/maps/model/Marker;", "onRequestPermissionsResult", "requestCode", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onViewCreated", "view", "app_debug"})
public final class CitasMapaFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.OnMapReadyCallback, com.google.android.gms.maps.GoogleMap.OnMarkerClickListener {
    @org.jetbrains.annotations.Nullable()
    private com.archivomedico.personal.databinding.FragmentCitasMapaBinding _binding;
    private com.google.android.gms.maps.GoogleMap mMap;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private com.google.android.libraries.places.api.net.PlacesClient placesClient;
    private final int LOCATION_PERMISSION_REQUEST = 1001;
    
    public CitasMapaFragment() {
        super();
    }
    
    private final com.archivomedico.personal.databinding.FragmentCitasMapaBinding getBinding() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    private final void checkLocationPermissionAndEnable() {
    }
    
    private final void enableUserLocation() {
    }
    
    private final void buscarLugaresCercanos(com.google.android.gms.maps.model.LatLng ubicacion) {
    }
    
    private final void buscarLugarPorTipo(com.google.android.libraries.places.api.model.Place.Type tipo) {
    }
    
    @java.lang.Override()
    public boolean onMarkerClick(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.Marker marker) {
        return false;
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}