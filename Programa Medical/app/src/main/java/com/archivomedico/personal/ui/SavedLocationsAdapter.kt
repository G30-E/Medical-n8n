package com.archivomedico.personal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.archivomedico.personal.R
import com.archivomedico.personal.data.SavedLocation

class SavedLocationsAdapter(
    private val onItemClick: (SavedLocation) -> Unit, // Nuevo listener para el clic en el elemento
    private val onDeleteClick: (SavedLocation) -> Unit
) : ListAdapter<SavedLocation, SavedLocationsAdapter.LocationViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location, onItemClick, onDeleteClick)
    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewLocationName)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDeleteLocation)

        fun bind(
            location: SavedLocation,
            onItemClick: (SavedLocation) -> Unit,
            onDeleteClick: (SavedLocation) -> Unit
        ) {
            nameTextView.text = location.name
            deleteButton.setOnClickListener { onDeleteClick(location) }
            itemView.setOnClickListener { onItemClick(location) } // Asigna el listener al elemento completo
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SavedLocation>() {
            override fun areItemsTheSame(oldItem: SavedLocation, newItem: SavedLocation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SavedLocation, newItem: SavedLocation): Boolean {
                return oldItem == newItem
            }
        }
    }
}