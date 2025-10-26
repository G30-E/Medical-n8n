package com.archivomedico.personal.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.archivomedico.personal.databinding.FragmentPerfilBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sexArrId = resources.getIdentifier("sex_options", "array", requireContext().packageName)
        val bloodArrId = resources.getIdentifier("blood_types", "array", requireContext().packageName)
        val sexos = if (sexArrId != 0) resources.getStringArray(sexArrId) else arrayOf("Masculino","Femenino","Otro")
        val tiposSangre = if (bloodArrId != 0) resources.getStringArray(bloodArrId) else arrayOf("O+","O-","A+","A-","B+","B-","AB+","AB-")

        fun setupDropDown(ac: MaterialAutoCompleteTextView?, data: Array<String>) {
            if (ac == null) return
            ac.setSimpleItems(data)
            ac.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) ac.showDropDown() }
            ac.setOnClickListener { ac.showDropDown() }
        }

        setupDropDown(binding.actSexo, sexos)
        setupDropDown(binding.actSangre, tiposSangre)

        binding.etAltura.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val onlyDigits = s?.toString()?.filter { it.isDigit() || it == '.' } ?: ""
                if (onlyDigits != s.toString()) {
                    binding.etAltura.removeTextChangedListener(this)
                    binding.etAltura.setText(onlyDigits)
                    binding.etAltura.setSelection(onlyDigits.length)
                    binding.etAltura.addTextChangedListener(this)
                }
            }
        })

        val auth = requireContext().getSharedPreferences("auth", 0)
        val email = auth.getString("email", auth.getString("loggedEmail", "")) ?: ""
        val sp = requireContext().getSharedPreferences("perfil_" + email.trim().lowercase(), 0)

        binding.etNombre.setText(sp.getString("nombre", ""))
        val rawEdad = sp.all["edad"]
        val edadStr = when (rawEdad) {
            is Int -> if (rawEdad >= 0) rawEdad.toString() else ""
            is Long -> if (rawEdad >= 0L) rawEdad.toString() else ""
            is String -> rawEdad
            else -> ""
        }
        binding.etEdad.setText(edadStr)
        binding.actSexo.setText(sp.getString("sexo", ""), false)
        binding.actSangre.setText(sp.getString("tipo_sangre", ""), false)
        binding.etExpediente.setText(sp.getString("expediente", ""))
        binding.etPeso.setText(sp.getString("peso", ""))
        binding.etAltura.setText(sp.getString("altura", ""))
        binding.etInfo.setText(sp.getString("info", ""))

        binding.btnGuardarPerfil.setOnClickListener {
            var ok = true
            binding.tilNombre.error = if (binding.etNombre.text.isNullOrBlank()) { ok = false; "Requerido" } else null
            binding.tilEdad.error = if (binding.etEdad.text.isNullOrBlank()) { ok = false; "Requerido" } else null
            val sexoVal = binding.actSexo.text?.toString()?.trim().orEmpty()
            binding.tilSexo.error = if (sexoVal.isEmpty()) { ok = false; "Selecciona una opción" } else null
            if (!ok) return@setOnClickListener

            sp.edit {
                putString("nombre", binding.etNombre.text.toString())
                val edadNum = binding.etEdad.text.toString().trim().toIntOrNull()
                remove("edad")
                if (edadNum != null) putInt("edad", edadNum) else putString("edad", binding.etEdad.text.toString().trim())
                putString("sexo", sexoVal)
                putString("tipo_sangre", binding.actSangre.text?.toString())
                putString("expediente", binding.etExpediente.text.toString())
                putString("peso", binding.etPeso.text.toString())
                putString("altura", binding.etAltura.text.toString())
                putString("info", binding.etInfo.text.toString())
                putBoolean("completed", true)
            }

            Toast.makeText(requireContext(), "Perfil guardado", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }

        // Volver al menú principal
        binding.btnVolverMenu.setOnClickListener {
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}