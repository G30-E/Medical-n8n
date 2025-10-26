package com.archivomedico.personal.data

import android.content.Context

class UserRepository(context: Context) {
    private val dao = AppDatabase.get(context).userDao()

    suspend fun isEmailTaken(email: String): Boolean =
        dao.getByEmail(email.trim()) != null

    suspend fun register(email: String, password: String): Result<Unit> {
        require(email.contains("@")) { "Correo inválido" }
        require(password.length >= 4) { "Contraseña muy corta" }
        return runCatching { dao.insert(User(email.trim(), password)) }
    }

    suspend fun canLogin(email: String, password: String): Boolean =
        dao.getByEmail(email.trim())?.password == password
    
    suspend fun resetPassword(email: String, newPassword: String): Result<Boolean> {
        require(email.contains("@")) { "Correo inválido" }
        require(newPassword.length >= 4) { "Contraseña muy corta" }
        return runCatching {
            val exists = isEmailTaken(email)
            if (!exists) return@runCatching false
            dao.updatePassword(email.trim(), newPassword) > 0
        }
    }
}
