package com.archivomedico.personal.ui
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.archivomedico.personal.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MedsViewModel(app: Application): AndroidViewModel(app) {
    suspend fun nameExists(name: String): Boolean = repo.nameExists(name)

    private val repo = MedsRepository(app)

    val meds: StateFlow<List<MedGroup>> =
        repo.getAllGrouped().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun saveGroup(group: MedGroup) = viewModelScope.launch {
                // Validaciones básicas
        if (group.name.trim().isEmpty()) return@launch
        if (group.times.isEmpty()) return@launch

        // Si el nombre ya existe, no lanzamos excepción; simplemente no guardamos (la UI ya mostró advertencia)
        if (repo.nameExists(group.name)) {
            return@launch
        }

        // Deduplicar horas por seguridad
        val unique = group.times.distinct()
        repo.saveGroup(group.copy(times = unique))
    }

    fun deleteGroup(group: MedGroup) = viewModelScope.launch {
        repo.deleteGroup(group)
    }
    
    fun replaceGroup(originalName: String, originalDose: String, originalDaysMask: Int, updated: MedGroup) = viewModelScope.launch {
        repo.replaceGroup(originalName, originalDose, originalDaysMask, updated)
    }


    suspend fun setActive(group: com.archivomedico.personal.data.MedGroup, active: Boolean) {
        repo.setActive(group, active)
    }
}

