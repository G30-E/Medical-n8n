package com.archivomedico.personal.ui

import android.Manifest
import android.content.Intent // Importar Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri // Importar Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast // Importar Toast
import androidx.appcompat.app.AlertDialog // Importar AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.archivomedico.personal.databinding.FragmentCitasMapaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// Clase de datos para almacenar información del lugar desde Nearby Search
data class SimplePlaceInfo(
    val name: String?,
    val latLng: LatLng,
    val phoneNumber: String?,
    val placeId: String?
)

class CitasMapaFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener { // Implementar OnMarkerClickListener
    private var _binding: FragmentCitasMapaBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private val LOCATION_PERMISSION_REQUEST = 1001

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCitasMapaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState) // Llamar a super
        // Inicializar Places
        if (!Places.isInitialized()) {
            Places.initialize(requireContext().applicationContext, "AIzaSyDMA0iQJywLNHSo7ldXVL5UPP9cBLqseFw") // API Key
        }
        placesClient = Places.createClient(requireContext())
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(com.archivomedico.personal.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.buscarLugarButton.setOnClickListener {
            val tipoTexto = binding.tipoLugarEditText.text.toString().trim().lowercase()
            val tipoGoogle = when {
                tipoTexto.contains("hospital") -> Place.Type.HOSPITAL
                tipoTexto.contains("farmacia") -> Place.Type.PHARMACY
                tipoTexto.contains("doctor") || tipoTexto.contains("médico") || tipoTexto.contains("medico") -> Place.Type.DOCTOR
                tipoTexto.contains("salud") || tipoTexto.contains("centro médico") || tipoTexto.contains("centro medico") -> Place.Type.HEALTH
                else -> null
            }
            if (tipoGoogle != null) {
                buscarLugarPorTipo(tipoGoogle)
            } else {
                Toast.makeText(requireContext(), "Tipo de lugar no reconocido", Toast.LENGTH_SHORT).show()
            }
        }

        // Volver al menú principal
        binding.btnVolverMenu.setOnClickListener {
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this) // Establecer el listener de clic en el marcador
        checkLocationPermissionAndEnable()
    }

    private fun checkLocationPermissionAndEnable() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation()
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    private fun enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                    buscarLugaresCercanos(userLatLng)
                }
            }
        }
    }

    private fun buscarLugaresCercanos(ubicacion: LatLng) {
        // Solicitar ID y número de teléfono además de los campos existentes
        val placeFields = listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.TYPES, Place.Field.ID, Place.Field.PHONE_NUMBER)
        val request = FindCurrentPlaceRequest.newInstance(placeFields)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
                mMap.clear() // Limpiar marcadores antiguos antes de agregar nuevos
                val healthTypes = setOf(
                    Place.Type.HOSPITAL,
                    Place.Type.PHARMACY,
                    Place.Type.DOCTOR,
                    Place.Type.HEALTH
                )
                for (placeLikelihood in response.placeLikelihoods) {
                    val place = placeLikelihood.place
                    val types = place.types ?: listOf()
                    if (types.isNotEmpty() && (healthTypes.contains(types[0]) || types.all { healthTypes.contains(it) })) {
                        val marker = mMap.addMarker(
                            MarkerOptions()
                                .position(place.latLng!!)
                                .title(place.name)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        )
                        marker?.tag = place // Almacenar el objeto Place completo en el tag del marcador
                    }
                }
                 // Mover la cámara a la ubicación del usuario después de agregar marcadores
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
            }.addOnFailureListener { exception ->
                // Manejar el error, por ejemplo, mostrar un Toast
                 Toast.makeText(context, "Error al buscar lugares cercanos: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun buscarLugarPorTipo(tipo: Place.Type) {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    val tipoString = when (tipo) {
                        Place.Type.HOSPITAL -> "hospital"
                        Place.Type.PHARMACY -> "pharmacy"
                        Place.Type.DOCTOR -> "doctor"
                        Place.Type.HEALTH -> "health" // Este tipo es más genérico, puede que necesitemos algo más específico.
                                                     // Google Places API (Nearby Search) usa 'health' para un espectro amplio.
                                                     // Para "centro médico", "hospital" o "doctor" podrían ser más directos si están disponibles.
                        else -> "hospital" // Default a hospital
                    }
                    val apiKey = "AIzaSyDMA0iQJywLNHSo7ldXVL5UPP9cBLqseFw" // API Key
                    // Radio más amplio para más resultados, considera hacerlo configurable o ajustarlo
                    val radius = 5000 // Aumentado a 5km
                    val urlString = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${userLatLng.latitude},${userLatLng.longitude}&radius=$radius&type=$tipoString&key=$apiKey"

                    // La siguiente parte hace una llamada de red en el hilo principal.
                    // Esto es MALO para producción. Debería usar coroutines o AsyncTask.
                    // Por ahora, para mantener la lógica original del usuario, lo dejamos con StrictMode.
                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)

                    try {
                        val url = URL(urlString)
                        val conn = url.openConnection() as HttpURLConnection
                        conn.requestMethod = "GET"
                        val response = conn.inputStream.bufferedReader().use { it.readText() }
                        val json = JSONObject(response)
                        val results = json.getJSONArray("results")

                        mMap.clear() // Limpiar marcadores antiguos
                        for (i in 0 until results.length()) {
                            val placeJson = results.getJSONObject(i)
                            val name = placeJson.getString("name")
                            val geometry = placeJson.getJSONObject("geometry")
                            val locationObj = geometry.getJSONObject("location")
                            val lat = locationObj.getDouble("lat")
                            val lng = locationObj.getDouble("lng")
                            val placeLatLng = LatLng(lat, lng)
                            val placeId = placeJson.optString("place_id", null)
                            // Intentar obtener el número de teléfono. Puede no estar siempre presente.
                            // La API Nearby Search puede devolver "formatted_phone_number" o "international_phone_number".
                            // Si no, se necesitaría una llamada a Place Details API usando el place_id.
                            // Por simplicidad, aquí solo tomamos lo que esté directamente disponible.
                            val phoneNumber = placeJson.optString("formatted_phone_number", placeJson.optString("international_phone_number", null))

                            val marker = mMap.addMarker(
                                MarkerOptions()
                                    .position(placeLatLng)
                                    .title(name)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                            )
                            // Almacenar SimplePlaceInfo en el tag del marcador
                            marker?.tag = SimplePlaceInfo(name, placeLatLng, phoneNumber, placeId)
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 12f)) // Zoom out un poco para ver más resultados
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Error al buscar por tipo: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } ?: Toast.makeText(context, "No se pudo obtener la ubicación actual.", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                 Toast.makeText(context, "Error al obtener la última ubicación: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val placeData = marker.tag
        var placeName: String? = null
        var placeLatLng: LatLng? = null
        var phoneNumber: String? = null
        // var placeIdForDetails: String? = null // Para futura llamada a Place Details si es necesario

        when (placeData) {
            is Place -> { // Objeto de Places API (de buscarLugaresCercanos)
                placeName = placeData.name
                placeLatLng = placeData.latLng
                phoneNumber = placeData.phoneNumber
                // placeIdForDetails = placeData.id
            }
            is SimplePlaceInfo -> { // Objeto de Nearby Search (de buscarLugarPorTipo)
                placeName = placeData.name
                placeLatLng = placeData.latLng
                phoneNumber = placeData.phoneNumber
                // placeIdForDetails = placeData.placeId
            }
            else -> {
                Toast.makeText(context, "No hay datos adicionales para este lugar.", Toast.LENGTH_SHORT).show()
                return false // No consumir el evento si no hay datos
            }
        }

        if (placeLatLng == null) {
             Toast.makeText(context, "No hay coordenadas para este lugar.", Toast.LENGTH_SHORT).show()
            return false
        }

        val options = mutableListOf<String>()
        options.add("Iniciar Viaje")
        if (!phoneNumber.isNullOrBlank()) {
            options.add("Llamar al negocio")
        }

        AlertDialog.Builder(requireContext())
            .setTitle(placeName ?: "Lugar seleccionado")
            .setItems(options.toTypedArray()) { dialog, which ->
                when (options[which]) {
                    "Iniciar Viaje" -> {
                        val gmmIntentUri = Uri.parse("google.navigation:q=${placeLatLng.latitude},${placeLatLng.longitude}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivity(mapIntent)
                        } else {
                            Toast.makeText(context, "Google Maps no está instalado.", Toast.LENGTH_SHORT).show()
                            // Opcionalmente, abrir en navegador:
                            // val webIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${placeLatLng.latitude},${placeLatLng.longitude}")
                            // val webIntent = Intent(Intent.ACTION_VIEW, webIntentUri)
                            // startActivity(webIntent)
                        }
                    }
                    "Llamar al negocio" -> {
                        if (!phoneNumber.isNullOrBlank()) {
                            val dialIntent = Intent(Intent.ACTION_DIAL)
                            dialIntent.data = Uri.parse("tel:$phoneNumber")
                             if (dialIntent.resolveActivity(requireActivity().packageManager) != null) {
                                startActivity(dialIntent)
                            } else {
                                Toast.makeText(context, "No se encontró aplicación para realizar llamadas.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()

        return true // Consumir el evento de clic
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // Llamar a super
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation()
        } else {
            // Permiso denegado, podrías mostrar un mensaje al usuario.
            Toast.makeText(context, "Permiso de ubicación denegado. Algunas funciones pueden no estar disponibles.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // mMap.clear() // Opcional: limpiar el mapa para liberar recursos, aunque SupportMapFragment debería manejarlo.
    }
}
