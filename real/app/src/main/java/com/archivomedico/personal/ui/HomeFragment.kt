package com.archivomedico.personal.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.archivomedico.personal.R
import com.archivomedico.personal.databinding.FragmentHomeBinding
import java.io.PrintWriter
import java.io.StringWriter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private fun writeLog(ctx: Context, text: String) {
        try {
            ctx.openFileOutput("ui_log.txt", Context.MODE_APPEND).use { fos ->
                fos.write((text + "\n").toByteArray())
            }
        } catch (_: Exception) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return try {
            _binding = FragmentHomeBinding.inflate(inflater, container, false).also { writeLog(requireContext(), "HomeFragment: binding inflated") }
            binding.root
        } catch (e: Exception) {
            // Registrar stacktrace en archivo para diagnóstico y devolver una vista fallback
            val sw = StringWriter(); e.printStackTrace(PrintWriter(sw))
            writeLog(requireContext(), "HomeFragment: error inflating binding: ${e.message}")
            writeLog(requireContext(), sw.toString())

            // Fallback: TextView simple para que el usuario vea algo
            val tv = TextView(requireContext())
            tv.text = "Menú (fallback)"
            tv.textSize = 18f
            tv
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            // Si binding es nulo, no intentar acceder
            if (_binding == null) return

            writeLog(requireContext(), "HomeFragment: onViewCreated called")

            binding.btnPerfil.setOnClickListener {
                writeLog(requireContext(), "HomeFragment: btnPerfil clicked")
                findNavController().navigate(R.id.perfilFragment)
            }
            binding.btnMeds.setOnClickListener {
                writeLog(requireContext(), "HomeFragment: btnMeds clicked")
                findNavController().navigate(R.id.medsFragment)
            }
            binding.btnHistorial.setOnClickListener {
                writeLog(requireContext(), "HomeFragment: btnHistorial clicked")
                findNavController().navigate(R.id.historialFragment)
            }
            binding.btnSintomas.setOnClickListener {
                writeLog(requireContext(), "HomeFragment: btnSintomas clicked")
                findNavController().navigate(R.id.sintomasFragment)
            }
            binding.btnCitas.setOnClickListener {
                writeLog(requireContext(), "HomeFragment: btnCitas clicked")
                findNavController().navigate(R.id.citasMapaFragment)
            }

        } catch (e: Exception) {
            writeLog(requireContext(), "HomeFragment: exception in onViewCreated: ${e.message}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
