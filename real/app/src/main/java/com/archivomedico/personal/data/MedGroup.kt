package com.archivomedico.personal.data

data class MedGroup(
    val name: String,
    val dose: String,
    val daysMask: Int,
    val times: List<Pair<Int, Int>>,
    val active: Boolean = false
)
