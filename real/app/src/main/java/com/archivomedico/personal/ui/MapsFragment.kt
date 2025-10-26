package com.archivomedico.personal.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.archivomedico.personal.R
import com.archivomedico.personal.data.AppDatabase
import com.archivomedico.personal.data.SavedLocation
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private var currentUserEmail: String? = null
    private lateinit var savedLocationsAdapter: SavedLocationsAdapter
    private var selectedMarker: Marker? = null
    private var selectedLatLng: LatLng? = null
    private val httpClient = OkHttpClient()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                enableMyLocation()
            } else {
                Toast.makeText(requireContext(), "Permiso de ubicación denegado.", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), com.archivomedico.personal.BuildConfig.MAPS_API_KEY)
        }
        placesClient = Places.createClient(requireContext())
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupRecyclerView(view)
        setupSaveButton(view)
        setupSearchView(view)
        setupBackButton(view)
        setupBackToMenuButton(view) // <- Se añade la llamada al nuevo botón

        lifecycleScope.launch {
            val userDao = AppDatabase.get(requireContext()).userDao()
            currentUserEmail = userDao.getAnyUser()?.email
            currentUserEmail?.let { email ->
                observeSavedLocations(email)
            }
        }
    }
    
    private fun setupBackButton(view: View) {
        val backButton: ImageButton = view.findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    // v- Se añade la función para el nuevo botón
    private fun setupBackToMenuButton(view: View) {
        val backToMenuButton: Button = view.findViewById(R.id.buttonBackToMenu)
        backToMenuButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkAndRequestLocationPermission()

        mMap.setOnMapClickListener { latLng ->
            selectedMarker?.remove()
            selectedLatLng = latLng
            selectedMarker = mMap.addMarker(MarkerOptions().position(latLng).title("Ubicación Seleccionada"))
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }

    private fun checkAndRequestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableMyLocation()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                } else {
                    val bogota = LatLng(4.60971, -74.08175)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 12f))
                }
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewSavedLocations)
        savedLocationsAdapter = SavedLocationsAdapter(
            onItemClick = { location ->
                val target = LatLng(location.latitude, location.longitude)
                selectedMarker?.remove()
                selectedLatLng = target
                selectedMarker = mMap.addMarker(MarkerOptions().position(target).title(location.name))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(target, 15f))
            },
            onDeleteClick = { location ->
                deleteLocation(location)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = savedLocationsAdapter
    }
    
    private fun setupSaveButton(view: View) {
        val saveButton: Button = view.findViewById(R.id.buttonSaveLocation)

        saveButton.setOnClickListener {
            if (selectedLatLng == null) {
                Toast.makeText(requireContext(), "Por favor, selecciona una ubicación en el mapa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_save_location, null)
            val locationNameEditText: EditText = dialogView.findViewById(R.id.editTextDialogLocationName)

            AlertDialog.Builder(requireContext())
                .setTitle("Nombrar Ubicación")
                .setView(dialogView)
                .setPositiveButton("Guardar") { dialog, _ ->
                    val locationName = locationNameEditText.text.toString()
                    val userEmail = currentUserEmail

                    if (locationName.isBlank()) {
                        Toast.makeText(requireContext(), "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
                    } else if (userEmail == null) {
                        Toast.makeText(requireContext(), "No se pudo identificar al usuario", Toast.LENGTH_SHORT).show()
                    } else {
                        val newLocation = SavedLocation(
                            name = locationName,
                            latitude = selectedLatLng!!.latitude,
                            longitude = selectedLatLng!!.longitude,
                            userEmail = userEmail
                        )

                        lifecycleScope.launch {
                            AppDatabase.get(requireContext()).savedLocationDao().insert(newLocation)
                            Toast.makeText(requireContext(), "Ubicación guardada", Toast.LENGTH_SHORT).show()
                            
                            sendDataToN8n(newLocation)

                            selectedMarker?.remove()
                            selectedMarker = null
                            selectedLatLng = null
                        }
                        dialog.dismiss()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    private fun observeSavedLocations(userEmail: String) {
        val savedLocationDao = AppDatabase.get(requireContext()).savedLocationDao()
        lifecycleScope.launch {
            savedLocationDao.getLocationsForUser(userEmail).collect { locations ->
                savedLocationsAdapter.submitList(locations)
            }
        }
    }
    
    private fun deleteLocation(location: SavedLocation) {
        lifecycleScope.launch {
            AppDatabase.get(requireContext()).savedLocationDao().delete(location)
            Toast.makeText(requireContext(), "'${location.name}' eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSearchView(view: View) {
        val searchView: SearchView = view.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchPlace(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
    
    private fun searchPlace(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            if (response.autocompletePredictions.isNotEmpty()) {
                val prediction = response.autocompletePredictions.first()
                val placeId = prediction.placeId
                val placeFields = listOf(Place.Field.LAT_LNG)

                val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener { fetchPlaceResponse ->
                    val place = fetchPlaceResponse.place
                    place.latLng?.let { latLng ->
                        selectedMarker?.remove()
                        selectedLatLng = latLng
                        selectedMarker = mMap.addMarker(MarkerOptions().position(latLng).title(prediction.getPrimaryText(null).toString()))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                }.addOnFailureListener { exception ->
                    Log.e("MapsFragment", "Error al obtener detalles del lugar", exception)
                }
            } else {
                Toast.makeText(requireContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Log.e("MapsFragment", "Error en la autocompletación", exception)
        }
    }

    private fun sendDataToN8n(location: SavedLocation) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val url = "https://primary-production-d6e1d.up.railway.app/webhook-test/citas"
                
                val json = JSONObject()
                json.put("name", location.name)
                json.put("latitude", location.latitude)
                json.put("longitude", location.longitude)
                json.put("userEmail", location.userEmail)

                val body = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                
                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                val response = httpClient.newCall(request).execute()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Datos enviados a n8n.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error al enviar datos a n8n.", Toast.LENGTH_SHORT).show()
                        Log.e("MapsFragment", "Error en n8n: ${response.body?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Fallo de conexión al enviar datos.", Toast.LENGTH_SHORT).show()
                    Log.e("MapsFragment", "Excepción al enviar a n8n", e)
                }
            }
        }
    }
}