package com.archivomedico.personal.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.archivomedico.personal.R
import com.archivomedico.personal.databinding.FragmentHistorialBinding
import com.archivomedico.personal.network.NoteData
import com.archivomedico.personal.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistorialFragment : Fragment() {

    private var _binding: FragmentHistorialBinding? = null
    private val binding get() = _binding!!

    private val gson = Gson()
    private lateinit var adapter: ArrayAdapter<HistorialNota>
    private val notas = mutableListOf<HistorialNota>()
    private val PREFS_KEY_PREFIX = "notas_historial_json_"
    private val NOTAS_KEY = "notas"

    private var userEmail: String = ""

    data class HistorialNota(val title: String, val body: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authPrefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
        userEmail = authPrefs.getString("email", "") ?: ""

        setupAdapter()
        loadNotes()

        binding.fabAddNote.setOnClickListener {
            showNoteDialog(null)
        }

        binding.listNotas.setOnItemClickListener { _, _, position, _ ->
            showNoteDialog(notas[position])
        }

        binding.btnVolverMenu.setOnClickListener {
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }
    }

    private fun setupAdapter() {
        adapter = object : ArrayAdapter<HistorialNota>(requireContext(), android.R.layout.simple_list_item_2, android.R.id.text1, notas) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val text1 = view.findViewById<TextView>(android.R.id.text1)
                val text2 = view.findViewById<TextView>(android.R.id.text2)
                val nota = getItem(position)
                text1.text = nota?.title
                text2.text = nota?.body
                return view
            }
        }
        binding.listNotas.adapter = adapter
    }

    private fun showNoteDialog(existingNote: HistorialNota?) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_note, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etNoteTitle)
        val etBody = dialogView.findViewById<EditText>(R.id.etNoteBody)

        etTitle.setText(existingNote?.title)
        etBody.setText(existingNote?.body)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle(if (existingNote == null) "Añadir Nota" else "Editar Nota")
            .setPositiveButton("Guardar") { dialog, _ ->
                val title = etTitle.text.toString().trim()
                val body = etBody.text.toString().trim()
                if (title.isNotEmpty() && body.isNotEmpty()) {
                    val newNote = HistorialNota(title, body)
                    if (existingNote != null) {
                        val index = notas.indexOf(existingNote)
                        if (index != -1) {
                            notas[index] = newNote
                        }
                    } else {
                        notas.add(newNote)
                    }
                    saveNotes()
                    sendToN8n(newNote)
                } else {
                    Toast.makeText(requireContext(), "El título y el cuerpo no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar", null)

        if (existingNote != null) {
            builder.setNeutralButton("Eliminar") { _, _ ->
                notas.remove(existingNote)
                saveNotes()
                Toast.makeText(requireContext(), "Nota eliminada", Toast.LENGTH_SHORT).show()
            }
        }

        builder.show()
    }

    private fun getPrefsName(): String {
        return PREFS_KEY_PREFIX + userEmail.trim().lowercase()
    }

    private fun saveNotes() {
        if (userEmail.isBlank()) return
        val prefs = requireContext().getSharedPreferences(getPrefsName(), Context.MODE_PRIVATE)
        val json = gson.toJson(notas)
        prefs.edit().putString(NOTAS_KEY, json).apply()
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }

    private fun loadNotes() {
        if (userEmail.isBlank()) return
        val prefs = requireContext().getSharedPreferences(getPrefsName(), Context.MODE_PRIVATE)
        val json = prefs.getString(NOTAS_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<HistorialNota>>() {}.type
            notas.clear()
            notas.addAll(gson.fromJson(json, type))
        }
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }

    private fun toggleEmptyView() {
        binding.tvEmpty.visibility = if (notas.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun sendToN8n(note: HistorialNota) {
        val profilePrefs = requireContext().getSharedPreferences("perfil_" + userEmail.trim().lowercase(), Context.MODE_PRIVATE)
        val patientName = profilePrefs.getString("nombre", "") ?: ""
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val noteData = NoteData(
            title = note.title,
            body = note.body,
            email = userEmail,
            timestamp = timestamp,
            patientName = patientName
        )

        RetrofitClient.n8nApiService.sendNote(noteData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("HistorialFragment", "Nota enviada a n8n con éxito")
                } else {
                    Log.e("HistorialFragment", "Error al enviar la nota a n8n: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("HistorialFragment", "Fallo al enviar la nota a n8n", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
