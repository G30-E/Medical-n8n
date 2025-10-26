package com.archivomedico.personal.ui
object DaysMaskFormatter {
    private val short = listOf("Do","Lu","Ma","Mi","Ju","Vi","Sa")
    fun format(mask: Int): String {
        val sel = mutableListOf<String>()
        for (i in 0..6) if ((mask and (1 shl i)) != 0) sel += short[i]
        return if (sel.isEmpty()) "—" else sel.joinToString(", ")
    }
}
