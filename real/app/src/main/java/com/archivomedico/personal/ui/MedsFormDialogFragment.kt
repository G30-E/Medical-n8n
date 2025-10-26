package com.archivomedico.personal.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.archivomedico.personal.data.MedGroup
import com.archivomedico.personal.databinding.DialogMedFormBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedsFormDialogFragment : DialogFragment() {

    private fun String?.orElse(fallback: String) = this ?: fallback


    private val vm: MedsViewModel by activityViewModels()
    private var _binding: DialogMedFormBinding? = null
    private val b get() = _binding!!

    // Internally keep times as 24h pairs
    private val times: MutableList<Pair<Int, Int>> = mutableListOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogMedFormBinding.inflate(LayoutInflater.from(requireContext()))

        // Spinner 1..6 para cantidad de dosis diarias (número de horas a elegir)
        val counts = (1..6).map { it.toString() }
        b.spCount.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, counts)
        b.spCount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val n = position + 1
                // mostrar/ocultar botones de hora
                timeButtons().forEachIndexed { idx, btn ->
                    btn.visibility = if (idx < n) View.VISIBLE else View.GONE
                }
                // asegurar tamaño de times
                ensureTimesSize(n)
                // refrescar etiquetas en 12h
                refreshTimeLabels()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Prefill desde argumentos (modo edición)
        val name = arguments?.getString(ARG_NAME) ?: ""
        val doseText = arguments?.getString(ARG_DOSE) ?: ""
        val daysMask = arguments?.getInt(ARG_DAYS) ?: 0
        val flat = arguments?.getIntegerArrayList(ARG_TIMES_FLAT) ?: arrayListOf()
        val active = arguments?.getBoolean(ARG_ACTIVE) ?: false

        b.etName.setText(name)
        b.etDose.setText(doseText)
        applyDaysToUi(daysMask)

        if (flat.size % 2 == 0 && flat.isNotEmpty()) {
            times.clear()
            for (i in 0 until flat.size step 2) {
                val h = flat[i]
                val m = flat[i + 1]
                times.add(h to m)
            }
            // seleccionar cantidad en spinner según tamaño de times
            val count = times.size.coerceIn(1, 6)
            b.spCount.setSelection(count - 1)
        } else {
            // default: 1 dosis
            ensureTimesSize(1)
            b.spCount.setSelection(0)
        }

        // Listeners para botones de hora (MaterialTimePicker 12h UI)
        timeButtons().forEachIndexed { idx, btn ->
            btn.setOnClickListener {
                val (h, m) = times.getOrNull(idx) ?: (8 to 0)
                showTimePicker12h(
                    doseIndex = idx,
                    initialH24 = h,
                    initialM = m
                ) { newH, newM, label12 ->
                    times[idx] = newH to newM
                    btn.text = labelForButton(idx, label12)
                }
            }
        }

        // Inicializar etiquetas de botones
        refreshTimeLabels()

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(if (name.isBlank()) "Agregar medicamento" else "Editar medicamento")
            .setView(b.root)
            .setPositiveButton("Guardar") { d, _ ->
                try {
                    val finalName = b.etName.text?.toString()?.trim().orEmpty()
                    val finalDose = b.etDose.text?.toString()?.trim().orEmpty()
                    require(finalName.isNotEmpty()) { "El nombre es obligatorio." }

                    val selectedDays = readDaysFromUi()
                    require(selectedDays != 0) { "Selecciona al menos un día." }

                    val count = (b.spCount.selectedItem as? String)?.toIntOrNull()?.coerceIn(1, 6) ?: 1
                    ensureTimesSize(count)

                    // Recorta a N
                    val chosen = times.take(count)

                    
                        // Validación: horas no repetidas
                        if (chosen.size != chosen.distinct().size) {
                            AlertDialog.Builder(requireContext())
                                .setMessage("Las horas no deben repetirse.")
                                .setPositiveButton("OK", null)
                                .show()
                            return@setPositiveButton
                        }

                        val group = MedGroup(
                        name = finalName,
                        dose = finalDose,
                        daysMask = selectedDays,
                        times = chosen,
                        active = active // se conserva si venía en argumentos; no hay switch en el formulario
                    )
                    val origName = arguments?.getString(ARG_ORIG_NAME)
                        val origDose = arguments?.getString(ARG_ORIG_DOSE)
                        val origDays = arguments?.getInt(ARG_ORIG_DAYS)
                        if (origName != null && origDose != null && origDays != null) {
                            // Editar: reemplazar por clave original para no duplicar sección
                            vm.replaceGroup(origName, origDose, origDays, group)
                        } else {
                            // Agregar nuevo
                            vm.saveGroup(group)
                        }
                } catch (e: Throwable) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(e.message ?: "Error al guardar")
                        .setPositiveButton("OK", null)
                        .show()
                    return@setPositiveButton
                }
                d.dismiss()
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener { di ->
    val alert = di as androidx.appcompat.app.AlertDialog
    val btn = alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
    btn.setOnClickListener {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val finalName = b.etName.text?.toString()?.trim().orEmpty()
                val finalDose = b.etDose.text?.toString()?.trim().orEmpty()
                if (finalName.isEmpty()) {
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setMessage("El nombre es obligatorio.")
                        .setPositiveButton("OK", null).show()
                    return@launch
                }
                val selectedDays = readDaysFromUi()
                if (selectedDays == 0) {
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setMessage("Selecciona al menos un día.")
                        .setPositiveButton("OK", null).show()
                    return@launch
                }
                val count = (b.spCount.selectedItem as? String)?.toIntOrNull()?.coerceIn(1, 6) ?: 1
                ensureTimesSize(count)
                val chosen = times.take(count)
                if (chosen.size != chosen.distinct().size) {
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setMessage("Las horas no deben repetirse.")
                        .setPositiveButton("OK", null).show()
                    return@launch
                }
                // Duplicate name guard (both add and edit rename)
                val origName = arguments?.getString(ARG_ORIG_NAME)
                val origDose = arguments?.getString(ARG_ORIG_DOSE)
                val origDays = arguments?.getInt(ARG_ORIG_DAYS)
                val editing = origName != null && origDose != null && origDays != null
                val exists = vm.nameExists(finalName)
                if ((!editing && exists) || (editing && finalName.lowercase() != origName!!.lowercase() && exists)) {
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setMessage("Ya existe un medicamento con ese nombre.")
                        .setPositiveButton("OK", null).show()
                    return@launch
                }
                val group = com.archivomedico.personal.data.MedGroup(
                    name = finalName,
                    dose = finalDose,
                    daysMask = selectedDays,
                    times = chosen,
                    active = arguments?.getBoolean(ARG_ACTIVE) ?: false
                )
                if (editing) {
                    vm.replaceGroup(origName!!, origDose!!, origDays!!, group)
                } else {
                    vm.saveGroup(group)
                }
                alert.dismiss()
            } catch (e: Throwable) {
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setMessage(e.message ?: "Error al guardar")
                    .setPositiveButton("OK", null).show()
            }
        }
    }
}

            return dialog
    }

    private fun timeButtons() = listOf(
        b.btnTime1, b.btnTime2, b.btnTime3, b.btnTime4, b.btnTime5, b.btnTime6
    )

    private fun ensureTimesSize(n: Int) {
        while (times.size < n) times.add(8 to 0) // 08:00 por defecto
        while (times.size > 6) times.removeLast()
    }

    private fun labelForButton(index: Int, label12: String): String =
        "Hora ${index + 1}: $label12"

    private fun refreshTimeLabels() {
        timeButtons().forEachIndexed { idx, btn ->
            val (h, m) = times.getOrNull(idx) ?: (8 to 0)
            val label = format12h(h, m)
            btn.text = labelForButton(idx, label)
        }
    }

    private fun format12h(hour24: Int, minute: Int): String {
        val ampm = if (hour24 >= 12) "PM" else "AM"
        val h12raw = hour24 % 12
        val h12 = if (h12raw == 0) 12 else h12raw
        return String.format(Locale.getDefault(), "%02d:%02d %s", h12, minute, ampm)
    }

    private fun showTimePicker12h(
        doseIndex: Int,
        initialH24: Int,
        initialM: Int,
        onPicked: (h24: Int, m: Int, label12: String) -> Unit
    ) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H) // UI en 12 h
            .setHour(initialH24)
            .setMinute(initialM)
            .setTitleText("Selecciona la hora de la dosis ${doseIndex + 1}")
            .build()

        picker.addOnPositiveButtonClickListener {
            val h = picker.hour
            val m = picker.minute
            onPicked(h, m, format12h(h, m))
        }
        picker.show(childFragmentManager, "dose_time_$doseIndex")
    }

    private fun applyDaysToUi(mask: Int) {
        b.cbSun.isChecked = (mask and (1 shl 0)) != 0
        b.cbMon.isChecked = (mask and (1 shl 1)) != 0
        b.cbTue.isChecked = (mask and (1 shl 2)) != 0
        b.cbWed.isChecked = (mask and (1 shl 3)) != 0
        b.cbThu.isChecked = (mask and (1 shl 4)) != 0
        b.cbFri.isChecked = (mask and (1 shl 5)) != 0
        b.cbSat.isChecked = (mask and (1 shl 6)) != 0
    }

    private fun readDaysFromUi(): Int {
        var mask = 0
        if (b.cbSun.isChecked) mask = mask or (1 shl 0)
        if (b.cbMon.isChecked) mask = mask or (1 shl 1)
        if (b.cbTue.isChecked) mask = mask or (1 shl 2)
        if (b.cbWed.isChecked) mask = mask or (1 shl 3)
        if (b.cbThu.isChecked) mask = mask or (1 shl 4)
        if (b.cbFri.isChecked) mask = mask or (1 shl 5)
        if (b.cbSat.isChecked) mask = mask or (1 shl 6)
        return mask
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_DOSE = "ARG_DOSE"
        private const val ARG_DAYS = "ARG_DAYS"
        private const val ARG_TIMES_FLAT = "ARG_TIMES_FLAT"
        private const val ARG_ACTIVE = "ARG_ACTIVE"
            private const val ARG_ORIG_NAME = "ARG_ORIG_NAME"
            private const val ARG_ORIG_DOSE = "ARG_ORIG_DOSE"
            private const val ARG_ORIG_DAYS = "ARG_ORIG_DAYS"

        fun newInstance(g: MedGroup): MedsFormDialogFragment {
            val f = MedsFormDialogFragment()
            val args = Bundle().apply {
                putString(ARG_NAME, g.name)
                putString(ARG_DOSE, g.dose)
                putInt(ARG_DAYS, g.daysMask)
                val flat = ArrayList<Int>()
                g.times.forEach { flat.add(it.first); flat.add(it.second) }
                putIntegerArrayList(ARG_TIMES_FLAT, flat)
                putBoolean(ARG_ACTIVE, g.active)
                    putString(ARG_ORIG_NAME, g.name)
                    putString(ARG_ORIG_DOSE, g.dose)
                    putInt(ARG_ORIG_DAYS, g.daysMask)
            }
            f.arguments = args
            return f
        }
    }
}