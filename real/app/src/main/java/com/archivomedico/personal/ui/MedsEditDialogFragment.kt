package com.archivomedico.personal.ui
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Diálogo antiguo reemplazado por MedsFormDialogFragment.
 * Se deja como stub para compatibilidad de compilación.
 */
class MedsEditDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Edición de medicamento")
            .setMessage("Usa el nuevo formulario (MedsFormDialogFragment) para agregar/editar.")
            .setPositiveButton("Aceptar", null)
            .create()
    }
}
