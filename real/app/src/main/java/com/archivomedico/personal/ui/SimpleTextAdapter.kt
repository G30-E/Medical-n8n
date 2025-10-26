
package com.archivomedico.personal.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleTextAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<SimpleTextAdapter.VH>() {
    class VH(val tv: TextView) : RecyclerView.ViewHolder(tv)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val tv = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return VH(tv)
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) { holder.tv.text = items[position] }
    fun add(text: String) { items.add(text); notifyItemInserted(items.lastIndex) }
}
