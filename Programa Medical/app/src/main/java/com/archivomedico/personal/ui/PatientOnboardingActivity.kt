package com.archivomedico.personal.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.archivomedico.personal.R
import com.archivomedico.personal.databinding.ActivityPatientOnboardingBinding

class PatientOnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientOnboardingBinding

    companion object {
        private const val TAG = "PatientOnboarding"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityPatientOnboardingBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val email = intent.getStringExtra("email")
                ?: getSharedPreferences("auth", MODE_PRIVATE).getString("email", null)

            // Configurar autocompletados usando R.array (sin reflection)
            try {
                val sexItems = resources.getStringArray(R.array.sex_options)
                val sexAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sexItems)
                binding.actSexo.setAdapter(sexAdapter)
                binding.actSexo.setOnClickListener { binding.actSexo.showDropDown() }
                binding.actSexo.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) binding.actSexo.showDropDown() }

                val bloodItems = resources.getStringArray(R.array.blood_types)
                val bloodAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bloodItems)
                binding.actSangre.setAdapter(bloodAdapter)
                binding.actSangre.setOnClickListener { binding.actSangre.showDropDown() }
                binding.actSangre.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) binding.actSangre.showDropDown() }
            } catch (e: Exception) {
                Log.w(TAG, "No se pudieron cargar arrays de recursos", e)
            }

            binding.btnGuardar.setOnClickListener {
                // Previene doble click mientras se procesa
                binding.btnGuardar.isEnabled = false
                try {
                    val nombre = binding.etNombre.text?.toString()?.trim().orEmpty()
                    val edadText = binding.etEdad.text?.toString()?.trim().orEmpty()
                    val edad = edadText.toIntOrNull() ?: -1
                    val expediente = binding.etExpediente.text?.toString()?.trim().orEmpty()
                    val peso = binding.etPeso.text?.toString()?.trim().orEmpty()
                    val altura = binding.etAltura.text?.toString()?.trim().orEmpty()
                    val genero = binding.actSexo.text?.toString()?.trim().orEmpty()
                    val sangre = binding.actSangre.text?.toString()?.trim().orEmpty()

                    // Validaciones básicas
                    if (nombre.isBlank() || edad < 0 || genero.isBlank()) {
                        Toast.makeText(this, "Complete los campos obligatorios (Nombre, Edad y Género)", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (edad > 120) {
                        Toast.makeText(this, "Ingrese una edad válida", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    if (email.isNullOrBlank()) {
                        Log.e(TAG, "Email nulo al guardar perfil. intentEmail=${intent.getStringExtra("email")}")
                        Toast.makeText(this, "No se pudo asociar el perfil al email", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    val prefsName = "perfil_" + email.trim().lowercase()
                    val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)

                    // Guardar de forma SÍNCRONA usando editor.commit() y comprobar resultado
                    val editor = prefs.edit()
                    editor.putString("nombre", nombre)
                    editor.putInt("edad", edad)
                    editor.putString("sexo", genero)
                    editor.putString("tipo_sangre", sangre)
                    editor.putString("expediente", expediente)
                    editor.putString("peso", peso)
                    editor.putString("altura", altura)
                    editor.putBoolean("completed", true)

                    val committed = try {
                        editor.commit()
                    } catch (e: Exception) {
                        Log.e(TAG, "commit() fallo", e)
                        false
                    }

                    if (!committed) {
                        Log.e(TAG, "No se pudieron guardar las preferencias de perfil")
                        Toast.makeText(this, "No se pudieron guardar los datos. Intente nuevamente.", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    // Asegurar que auth tenga el email asociado para futuras comprobaciones
                    try {
                        val auth = getSharedPreferences("auth", MODE_PRIVATE)
                        auth.edit().apply {
                            putString("email", email)
                            putString("loggedEmail", email)
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "No se pudo actualizar auth prefs: ${e.message}")
                    }

                    // Navegar a MainActivity solo si el commit fue exitoso. Evitar usar CLEAR_TASK que
                    // puede dejar la aplicación sin actividades si se hace mal; usar CLEAR_TOP+SINGLE_TOP
                    try {
                        val intentMain = Intent(this, MainActivity::class.java)
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intentMain)
                        finish()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error al iniciar MainActivity después de guardar", e)
                        Toast.makeText(this, "Perfil guardado pero no se pudo abrir la pantalla principal.", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    // Captura cualquier excepción inesperada y evita que la app se cierre
                    Log.e(TAG, "Error inesperado al guardar perfil", e)
                    Toast.makeText(this, "Ocurrió un error al guardar. Intente de nuevo.", Toast.LENGTH_LONG).show()
                } finally {
                    // Reactivar botón siempre
                    binding.btnGuardar.isEnabled = true
                }
            }

            binding.btnSalir.setOnClickListener {
                try {
                    getSharedPreferences("auth", MODE_PRIVATE).edit { putBoolean("keepSignedIn", false) }
                    startActivity(Intent(this, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()
                } catch (e: Exception) {
                    Log.e(TAG, "Error al salir", e)
                    Toast.makeText(this, "No se pudo salir correctamente.", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error en onCreate", e)
            Toast.makeText(this, "Ocurrió un error al abrir el formulario. Intente reiniciar la app.", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
