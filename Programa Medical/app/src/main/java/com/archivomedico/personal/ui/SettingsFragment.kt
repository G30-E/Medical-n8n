package com.archivomedico.personal.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.archivomedico.personal.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnLogout.setOnClickListener {
            requireContext().getSharedPreferences("auth", android.content.Context.MODE_PRIVATE).edit()
                .putBoolean("keepSignedIn", false)
                .remove("loggedEmail")
                .apply()
            startActivity(Intent(requireContext(), LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            requireActivity().finish()
        }

        binding.btnShareApp.setOnClickListener {
            val ctx = requireContext()
            val appName = try { ctx.getString(com.archivomedico.personal.R.string.app_name) } catch (_: Throwable) { "Archivo Médico Personal" }
            val sendIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(android.content.Intent.EXTRA_SUBJECT, appName)
                putExtra(android.content.Intent.EXTRA_TEXT, ctx.getString(com.archivomedico.personal.R.string.share_app_message, appName))
            }
            startActivity(android.content.Intent.createChooser(sendIntent, ctx.getString(com.archivomedico.personal.R.string.compartir_app)))
        }


        // Inicializar switch de modo oscuro según preferencia guardada
        val prefs = requireContext().getSharedPreferences("settings", 0)
        val darkMode = prefs.getBoolean("dark_mode", false)
        binding.switchDarkMode.isChecked = darkMode
        AppCompatDelegate.setDefaultNightMode(if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
            // Recrear la actividad para aplicar los cambios de tema de forma inmediata
            requireActivity().recreate()
        }

        // Volver al menú principal
        binding.btnVolverMenu.setOnClickListener {
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }

    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
