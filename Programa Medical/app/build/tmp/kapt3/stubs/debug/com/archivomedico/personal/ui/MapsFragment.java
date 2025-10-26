package com.archivomedico.personal.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J&\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0010\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\tH\u0016J\u001a\u0010\'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\u0010\u0010)\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u0005H\u0002J\u0010\u0010+\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010,\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001eH\u0002J\u0010\u0010-\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001eH\u0002J\u0010\u0010.\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001eH\u0002J\u0010\u0010/\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001eH\u0002J\u0010\u00100\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u001eH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0010\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00050\u00050\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/archivomedico/personal/ui/MapsFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/google/android/gms/maps/OnMapReadyCallback;", "()V", "currentUserEmail", "", "httpClient", "Lokhttp3/OkHttpClient;", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "placesClient", "Lcom/google/android/libraries/places/api/net/PlacesClient;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "kotlin.jvm.PlatformType", "savedLocationsAdapter", "Lcom/archivomedico/personal/ui/SavedLocationsAdapter;", "selectedLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "selectedMarker", "Lcom/google/android/gms/maps/model/Marker;", "checkAndRequestLocationPermission", "", "deleteLocation", "location", "Lcom/archivomedico/personal/data/SavedLocation;", "enableMyLocation", "observeSavedLocations", "userEmail", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onMapReady", "googleMap", "onViewCreated", "view", "searchPlace", "query", "sendDataToN8n", "setupBackButton", "setupBackToMenuButton", "setupRecyclerView", "setupSaveButton", "setupSearchView", "app_debug"})
public final class MapsFragment extends androidx.fragment.app.Fragment implements com.google.android.gms.maps.OnMapReadyCallback {
    private com.google.android.gms.maps.GoogleMap mMap;
    private com.google.android.libraries.places.api.net.PlacesClient placesClient;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentUserEmail;
    private com.archivomedico.personal.ui.SavedLocationsAdapter savedLocationsAdapter;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.Marker selectedMarker;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.maps.model.LatLng selectedLatLng;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient httpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestPermissionLauncher = null;
    
    public MapsFragment() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
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
    
    private final void setupBackButton(android.view.View view) {
    }
    
    private final void setupBackToMenuButton(android.view.View view) {
    }
    
    @java.lang.Override()
    public void onMapReady(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap googleMap) {
    }
    
    private final void checkAndRequestLocationPermission() {
    }
    
    private final void enableMyLocation() {
    }
    
    private final void setupRecyclerView(android.view.View view) {
    }
    
    private final void setupSaveButton(android.view.View view) {
    }
    
    private final void observeSavedLocations(java.lang.String userEmail) {
    }
    
    private final void deleteLocation(com.archivomedico.personal.data.SavedLocation location) {
    }
    
    private final void setupSearchView(android.view.View view) {
    }
    
    private final void searchPlace(java.lang.String query) {
    }
    
    private final void sendDataToN8n(com.archivomedico.personal.data.SavedLocation location) {
    }
}