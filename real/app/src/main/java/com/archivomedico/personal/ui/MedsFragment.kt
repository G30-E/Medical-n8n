package com.archivomedico.personal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.archivomedico.personal.R
import kotlinx.coroutines.launch

class MedsFragment : Fragment() {

    private val vmLazy: MedsViewModel by activityViewModels()
    private lateinit var adapter: MedsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_meds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvMeds)
        val btnAddTop = view.findViewById<Button>(R.id.btnAgregarMedTop)
        val btnVolver = view.findViewById<Button?>(R.id.btnVolverMenu)

        adapter = MedsAdapter(
            onEdit = { g -> MedsFormDialogFragment.newInstance(g).show(parentFragmentManager, "med_form") },
            onDelete = { g -> vmLazy.deleteGroup(g) },
            onToggle = { g, checked -> viewLifecycleOwner.lifecycleScope.launch { vmLazy.setActive(g, checked) } }
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        btnAddTop.setOnClickListener {
            MedsFormDialogFragment().show(parentFragmentManager, "med_form")
        }

        btnVolver?.setOnClickListener {
            findNavController().popBackStack(findNavController().graph.startDestinationId, false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vmLazy.meds.collect { list ->
                adapter.submit(list)
            }
        }
    }
}
