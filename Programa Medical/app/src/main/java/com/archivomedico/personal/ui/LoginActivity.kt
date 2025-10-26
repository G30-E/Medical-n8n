package com.archivomedico.personal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.archivomedico.personal.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputEditText
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import com.archivomedico.personal.data.UserRepository
import kotlinx.coroutines.launch
import com.google.android.material.checkbox.MaterialCheckBox

class LoginActivity : AppCompatActivity() {

    private lateinit var repo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Regla: solo saltar a Main si keepSignedIn=true y el perfil está completo
        val auth = getSharedPreferences("auth", MODE_PRIVATE)
        val keep = auth.getBoolean("keepSignedIn", false)
        val savedEmail = auth.getString("email", null) ?: auth.getString("loggedEmail", null)
        if (keep && !savedEmail.isNullOrBlank()) {
            if (isProfileComplete(savedEmail)) {
                goToMain()
            } else {
                goToOnboarding(savedEmail)
            }
            return
        }

        setContentView(R.layout.activity_login)
        val ctx = this
        repo = UserRepository(ctx)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        // Obtener el TextInputLayout (padre del EditText) y activar el ojito con animación
        val tilPassword = (etPassword.parent as? com.google.android.material.textfield.TextInputLayout)
            ?: (etPassword.parent?.parent as? com.google.android.material.textfield.TextInputLayout)
        tilPassword?.endIconMode = com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
        val tvForgot = findViewById<android.widget.TextView>(R.id.tvForgotPassword)
        tvForgot?.setOnClickListener { showForgotPasswordDialog() }

        val cbKeep: MaterialCheckBox? = findViewById(R.id.cbMantenerSesion)

        btnLogin.setOnClickListener {
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass = etPassword.text?.toString()?.trim().orEmpty()
            if (email.isBlank() || pass.isBlank()) {
                Toast.makeText(this, "Ingrese correo y contraseña", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val ok = repo.canLogin(email, pass)
                    if (!ok) {
                        Toast.makeText(this@LoginActivity, "Credenciales inválidas. Regístrese primero.", Toast.LENGTH_LONG).show()
                        return@launch
                    }

                    val keepNow = cbKeep?.isChecked ?: false
                    getSharedPreferences("auth", MODE_PRIVATE).edit()
                        .putBoolean("keepSignedIn", keepNow)
                        .putString("email", email)
                        .putString("loggedEmail", email)
                        .apply()

                    if (isProfileComplete(email)) {
                        goToMain()
                    } else {
                        goToOnboarding(email)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Hubo un problema al iniciar", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }

    private fun goToOnboarding(email: String) {
        startActivity(Intent(this, PatientOnboardingActivity::class.java)
            .putExtra("email", email)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        finish()
    }

    private fun isProfileComplete(email: String): Boolean {
        return try {
            val p = getSharedPreferences("perfil_" + email.trim().lowercase(), MODE_PRIVATE)
            if (p.getBoolean("completed", false)) return true
            val nombre = p.getString("nombre", null)
            val sexo = p.getString("sexo", null)
            val edadInt = if (p.contains("edad")) {
                try { p.getInt("edad", -1) } catch (e: ClassCastException) {
                    try { p.getString("edad", null)?.toIntOrNull() ?: -1 } catch (_: Exception) { -1 }
                }
            } else -1
            !nombre.isNullOrBlank() && !sexo.isNullOrBlank() && edadInt >= 0
        } catch (e: Exception) {
            false
        }
    }

    private fun showForgotPasswordDialog() {
        val ctx = this
        val container = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 8, 32, 8)
        }
        fun til(hintRes: Int, inputType: Int = android.text.InputType.TYPE_CLASS_TEXT, endToggle: Boolean = false): Pair<TextInputLayout, TextInputEditText> {
            val til = TextInputLayout(ctx).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                setHint(getString(hintRes))
                if (endToggle) endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            }
            val et = TextInputEditText(til.context).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                this.inputType = inputType
            }
            til.addView(et)
            return til to et
        }
        val (tilEmail, etEmail) = til(R.string.email, android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        val (tilNew, etNew) = til(R.string.new_password, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD, true)
        val (tilConf, etConf) = til(R.string.confirm_password, android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD, true)

        // Prefill email from login field if available
        val etLoginEmail = findViewById<EditText>(R.id.etEmail)
        etEmail.setText(etLoginEmail?.text?.toString()?.trim())

        container.addView(tilEmail)
        container.addView(tilNew)
        container.addView(tilConf)

        val dialog = MaterialAlertDialogBuilder(ctx)
            .setTitle(getString(R.string.reset_password))
            .setView(container)
            .setPositiveButton(getString(R.string.reset_password), null)
            .setNegativeButton(android.R.string.cancel, null)
            .create()

        dialog.setOnShowListener {
            val btn = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
            btn.setOnClickListener {
                val email = etEmail.text?.toString()?.trim().orEmpty()
                val pass1 = etNew.text?.toString().orEmpty()
                val pass2 = etConf.text?.toString().orEmpty()

                tilEmail.error = null; tilNew.error = null; tilConf.error = null

                if (!email.contains("@")) { tilEmail.error = getString(R.string.email); return@setOnClickListener }
                if (pass1.length < 4) { tilNew.error = getString(R.string.password_too_short); return@setOnClickListener }
                if (pass1 != pass2) { tilConf.error = getString(R.string.passwords_do_not_match); return@setOnClickListener }

                lifecycleScope.launch {
                    val exists = repo.isEmailTaken(email)
                    if (!exists) {
                        tilEmail.error = getString(R.string.email_not_found)
                        return@launch
                    }
                    val ok = repo.resetPassword(email, pass1).getOrElse { false }
                    if (ok) {
                        com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), getString(R.string.password_updated), com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setBackgroundTint(0xFF2E7D32.toInt()).setTextColor(0xFFFFFFFF.toInt()).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(ctx, "No se pudo actualizar la contraseña.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        dialog.show()
    }
    
}