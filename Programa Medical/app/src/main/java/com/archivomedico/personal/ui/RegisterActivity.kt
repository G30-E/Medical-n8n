package com.archivomedico.personal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.archivomedico.personal.data.UserRepository
import com.archivomedico.personal.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import android.view.animation.AccelerateDecelerateInterpolator

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var repo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = UserRepository(this)

        // Pequeño pulse al tocar el ojito (end icon) del campo de contraseña
        val tilPassword = binding.tilPassword
        val etPassword = binding.etPassword as EditText

        binding.btnCrearCuenta.setOnClickListener {
            val email = binding.etEmail.text?.toString()?.trim().orEmpty()
            val pass = binding.etPassword.text?.toString()?.trim().orEmpty()

            if (!email.contains("@")) {
                Snackbar.make(binding.root, "Correo inválido", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.length < 4) {
                Snackbar.make(binding.root, "La contraseña es muy corta (mínimo 4)", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val res = repo.register(email, pass)
                if (res.isSuccess) {
                    Snackbar.make(binding.root, "Cuenta creada", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(0xFF2E7D32.toInt()).setTextColor(0xFFFFFFFF.toInt()).show()
                    // Ir al login
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                } else {
                    val msg = res.exceptionOrNull()?.message ?: "No se pudo crear la cuenta"
                    Snackbar.make(binding.root, "Error: $msg", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.btnSalir.setOnClickListener { finish() }
    }
}