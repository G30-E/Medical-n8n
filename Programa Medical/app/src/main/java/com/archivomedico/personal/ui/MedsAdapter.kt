package com.archivomedico.personal.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.archivomedico.personal.data.MedGroup
import com.archivomedico.personal.databinding.ItemMedBinding
import java.util.Locale

class MedsAdapter(
    private val onEdit: (MedGroup) -> Unit,
    private val onDelete: (MedGroup) -> Unit,
    private val onToggle: (MedGroup, Boolean) -> Unit
) : RecyclerView.Adapter<MedsAdapter.H>() {

    private var items: List<MedGroup> = emptyList()

    class H(val b: ItemMedBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val inf = LayoutInflater.from(parent.context)
        return H(ItemMedBinding.inflate(inf, parent, false))
    }

    override fun onBindViewHolder(h: H, position: Int) {
        val g = items[position]
        h.b.tvName.text = g.name
        h.b.tvDose.text = if (g.dose.isBlank()) "" else "Dosis: ${g.dose}"

        // Mostrar horas (12h amigable)
        val times12 = g.times.joinToString(separator = ", ") { (h24, m) ->
            val ampm = if (h24 >= 12) "PM" else "AM"
            val h12raw = h24 % 12
            val h12 = if (h12raw == 0) 12 else h12raw
            String.format(Locale.getDefault(), "%02d:%02d %s", h12, m, ampm)
        }
        h.b.tvTime.text = if (times12.isBlank()) "" else "Horas: $times12"

        // Mostrar días como iniciales separadas por comas (Do, Lu, Ma, Mi, Ju, Vi, Sa)
        h.b.tvDay.text = "" // limpiar cualquier valor previo
        val daysText = maskToInitials(g.daysMask)
        h.b.tvDays.text = if (daysText.isBlank()) "Días: —" else "Días: $daysText"

        // Toggle (oculto en el layout, pero dejamos lógica intacta)
        h.b.cbActive.isChecked = g.active
        h.b.cbActive.setOnCheckedChangeListener(null)
        h.b.cbActive.setOnCheckedChangeListener { _, checked -> onToggle(g, checked) }

        h.b.btnEdit.setOnClickListener { onEdit(g) }
        h.b.btnDelete.setOnClickListener { onDelete(g) }
    }

    override fun getItemCount(): Int = items.size

    fun submit(list: List<MedGroup>) {
        items = list
        notifyDataSetChanged()
    }

    private fun maskToInitials(mask: Int): String {
        // Bits: 0=Do,1=Lu,2=Ma,3=Mi,4=Ju,5=Vi,6=Sa
        val labels = arrayOf("Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa")
        val parts = mutableListOf<String>()
        for (i in 0..6) {
            if ((mask and (1 shl i)) != 0) parts.add(labels[i])
        }
        return parts.joinToString(", ")
    }
}