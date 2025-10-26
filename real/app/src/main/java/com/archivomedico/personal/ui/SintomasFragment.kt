package com.archivomedico.personal.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.archivomedico.personal.R
import com.archivomedico.personal.databinding.FragmentSintomasBinding
import com.archivomedico.personal.network.NoteData
import com.archivomedico.personal.network.RetrofitClient
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// --- Data Classes movidas fuera del Fragment ---
data class SymptomHistoryItem(val symptoms: String, val diagnosis: String)
data class OpenAIRequest(val model: String = "gpt-3.5-turbo", val messages: List<Message>)
data class Message(val role: String, val content: String)
data class OpenAIResponse(val choices: List<Choice>)
data class Choice(val message: Message)

interface OpenAIApi {
    @POST("v1/chat/completions")
    fun getDiagnosis(@Body body: OpenAIRequest): Call<OpenAIResponse>
}

class SintomasFragment : Fragment() {
    private var _binding: FragmentSintomasBinding? = null
    private val binding get() = _binding!!
    private val apiKey = "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"

    private lateinit var adapter: ArrayAdapter<SymptomHistoryItem>
    private val historyList = mutableListOf<SymptomHistoryItem>()
    private val gson = Gson()
    private val PREFS_NAME_PREFIX = "diagnosticos_json_"
    private val HISTORY_KEY = "historial"

    private var userEmail: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSintomasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authPrefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
        userEmail = authPrefs.getString("email", "") ?: ""

        setupListView()
        loadHistory()

        val openAIApi = setupOpenAIApi()

        binding.btnAnalizar.setOnClickListener {
            val sintomas = binding.etSintomas.text.toString().trim()
            if (sintomas.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.por_favor_describe), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.btnAnalizar.isEnabled = false
            binding.btnAnalizar.text = getString(R.string.analizando)

            performAnalysis(openAIApi, sintomas)
        }

        binding.btnVolverMenu.setOnClickListener {
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }
    }

    private fun setupListView() {
        adapter = object : ArrayAdapter<SymptomHistoryItem>(requireContext(), R.layout.item_symptom_history, historyList) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_symptom_history, parent, false)
                val item = getItem(position)

                val tvSymptomContent = view.findViewById<TextView>(R.id.tvSymptomContent)
                val tvDiagnosisContent = view.findViewById<TextView>(R.id.tvDiagnosisContent)

                tvSymptomContent.text = item?.symptoms
                tvDiagnosisContent.text = item?.diagnosis

                return view
            }
        }
        binding.lvHistory.adapter = adapter
    }

    private fun performAnalysis(openAIApi: OpenAIApi, sintomas: String) {
        val systemPrompt = Message("system", "Eres un médico que da diagnósticos aproximados y recomendaciones.")
        val userPrompt = Message("user", sintomas)
        val request = OpenAIRequest(messages = listOf(systemPrompt, userPrompt))

        openAIApi.getDiagnosis(request).enqueue(object : Callback<OpenAIResponse> {
            override fun onResponse(call: Call<OpenAIResponse>, response: Response<OpenAIResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.choices?.firstOrNull()?.message?.content ?: "No se pudo obtener diagnóstico."
                    val newItem = SymptomHistoryItem(sintomas, result)

                    historyList.add(0, newItem) // Add to top of the list
                    saveHistory()
                    adapter.notifyDataSetChanged()
                    toggleEmptyView()

                    binding.etSintomas.text.clear()
                    Toast.makeText(requireContext(), "Análisis completado", Toast.LENGTH_SHORT).show()

                    sendToN8n(newItem)
                } else {
                    val errorBody = response.errorBody()?.string() ?: ""
                    Toast.makeText(requireContext(), "Error: ${response.code()} - $errorBody", Toast.LENGTH_LONG).show()
                }
                resetAnalyzeButton()
            }

            override fun onFailure(call: Call<OpenAIResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Fallo en la conexión: ${t.message}", Toast.LENGTH_LONG).show()
                resetAnalyzeButton()
            }
        })
    }

    private fun getPrefsName(): String {
        return PREFS_NAME_PREFIX + userEmail.trim().lowercase()
    }

    private fun saveHistory() {
        if (userEmail.isBlank()) return
        val prefs = requireContext().getSharedPreferences(getPrefsName(), Context.MODE_PRIVATE)
        val json = gson.toJson(historyList)
        prefs.edit().putString(HISTORY_KEY, json).commit()
    }

    private fun loadHistory() {
        if (userEmail.isBlank()) return
        val prefs = requireContext().getSharedPreferences(getPrefsName(), Context.MODE_PRIVATE)
        val json = prefs.getString(HISTORY_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<SymptomHistoryItem>>() {}.type
            historyList.clear()
            historyList.addAll(gson.fromJson(json, type))
        }
        adapter.notifyDataSetChanged()
        toggleEmptyView()
    }

    private fun toggleEmptyView() {
        binding.tvEmptyHistory.visibility = if (historyList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun resetAnalyzeButton() {
        binding.btnAnalizar.isEnabled = true
        binding.btnAnalizar.text = getString(R.string.analizar_ia)
    }

    private fun setupOpenAIApi(): OpenAIApi {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
            chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(authInterceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(OpenAIApi::class.java)
    }

    private fun sendToN8n(historyItem: SymptomHistoryItem) {
        val profilePrefs = requireContext().getSharedPreferences("perfil_" + userEmail.trim().lowercase(), Context.MODE_PRIVATE)
        val patientName = profilePrefs.getString("nombre", "") ?: ""
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val noteData = NoteData(
            title = "Síntomas: ${historyItem.symptoms}",
            body = "Diagnóstico: ${historyItem.diagnosis}",
            email = userEmail,
            timestamp = timestamp,
            patientName = patientName
        )

        RetrofitClient.n8nApiService.sendNote(noteData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("SintomasFragment", "Historial enviado a n8n con éxito")
                } else {
                    Log.e("SintomasFragment", "Error al enviar historial a n8n: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SintomasFragment", "Fallo al enviar historial a n8n", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
