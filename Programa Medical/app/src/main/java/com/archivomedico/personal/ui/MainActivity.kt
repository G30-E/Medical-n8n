package com.archivomedico.personal.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.archivomedico.personal.R
import com.archivomedico.personal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private fun isProfileComplete(email: String): Boolean {
        return try {
            val p = getSharedPreferences("perfil_" + email.trim().lowercase(), MODE_PRIVATE)
            if (p.getBoolean("completed", false)) return true
            val nombre = p.getString("nombre", null)
            val edad = p.getInt("edad", -1)
            val sexo = p.getString("sexo", null)
            !nombre.isNullOrBlank() && edad >= 0 && !sexo.isNullOrBlank()
        } catch (_: Exception) {
            false
        }
    }
    private lateinit var binding: ActivityMainBinding

    private fun writeLog(text: String) {
        try {
            openFileOutput("ui_log.txt", MODE_APPEND).use { fos ->
                fos.write((System.currentTimeMillis().toString() + ": " + text + "\n").toByteArray())
            }
        } catch (_: Exception) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        writeLog("MainActivity: onCreate start")
        // Aplicar modo oscuro según preferencia guardada antes de inflar la UI
        val settings = getSharedPreferences("settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        writeLog("MainActivity: setContentView done")

        // Establecer toolbar
        try {
            setSupportActionBar(binding.toolbar)
            writeLog("MainActivity: toolbar set")
        } catch (e: Exception) {
            android.util.Log.w("MainActivity", "No se pudo establecer toolbar como supportActionBar", e)
            writeLog("MainActivity: toolbar set failed: ${e.message}")
        }

        // Obtener NavController de forma robusta
        val navController = try {
            findNavController(R.id.nav_host_fragment)
        } catch (e: Exception) {
            android.util.Log.w("MainActivity", "findNavController falló, intentando alternativo", e)
            writeLog("MainActivity: findNavController failed: ${e.message}")
            // fallback: intentar recuperar NavHostFragment
            try {
                val frag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                val field = frag?.javaClass?.methods?.firstOrNull { it.name == "getNavController" }
                val nc = field?.invoke(frag)
                nc as? androidx.navigation.NavController
            } catch (e2: Exception) {
                android.util.Log.e("MainActivity", "No se pudo obtener NavController", e2)
                writeLog("MainActivity: fallback navController failed: ${e2.message}")
                null
            }
        }

        // Forzar que el navController cargue el nav_graph si es la primera creación
        try {
            if (savedInstanceState == null && navController != null) {
                try {
                    navController.setGraph(R.xml.nav_graph)
                    writeLog("MainActivity: setGraph nav_graph success")
                } catch (e: Exception) {
                    android.util.Log.e("MainActivity", "Error al setGraph nav_graph", e)
                    writeLog("MainActivity: setGraph failed: ${e.message}")
                }
            } else {
                writeLog("MainActivity: savedInstanceState not null or navController null")
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Error comprobando savedInstanceState", e)
            writeLog("MainActivity: savedInstanceState check failed: ${e.message}")
        }

        // Asegurar que bottomNav esté visible y vincular con NavController
        binding.bottomNav.visibility = View.VISIBLE
        try {
            navController?.let { binding.bottomNav.setupWithNavController(it) }
            writeLog("MainActivity: bottomNav setupWithNavController")
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Error vinculando bottomNav con navController", e)
            writeLog("MainActivity: bottomNav setup failed: ${e.message}")
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    if (navController == null) return@setOnItemSelectedListener true
                    val opts = navOptions {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                        restoreState = false
                    }
                    navController.navigate(R.id.homeFragment, null, opts)
                    true
                }
                else -> {
                    // default behavior for other tabs
                    if (navController != null) androidx.navigation.ui.NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }
        binding.bottomNav.setOnItemReselectedListener {
            try {
                navController?.popBackStack(navController.graph.startDestinationId, false)
            } catch (_: Exception) {}
        }
        writeLog("MainActivity: onCreate end")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                getSharedPreferences("auth", MODE_PRIVATE).edit {
                    putBoolean("keepSignedIn", false)
                    remove("loggedEmail")
                }
                startActivity(Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        writeLog("MainActivity: onResume")
        try {
            val prefsAuth = getSharedPreferences("auth", MODE_PRIVATE)
            val email = prefsAuth.getString("email", prefsAuth.getString("loggedEmail", "")) ?: ""
            writeLog("MainActivity: auth email=$email")
            if (email.isNotBlank() && !isProfileComplete(email)) {
                try {
                    // Iniciar la actividad de onboarding SIN modificar la task stack para evitar loops
                    startActivity(Intent(this, PatientOnboardingActivity::class.java).putExtra("email", email))
                    // Cerrar MainActivity para que el usuario complete el perfil
                    finish()
                } catch (e: Exception) {
                    // Log y notificar, evitar que la app se cierre
                    android.util.Log.e("MainActivity", "Error al iniciar PatientOnboardingActivity en onResume", e)
                    writeLog("MainActivity: error starting onboarding: ${e.message}")
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Error en onResume al comprobar perfil", e)
            writeLog("MainActivity: onResume check failed: ${e.message}")
        }
    }
}
