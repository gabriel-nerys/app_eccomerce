package com.example.projeto_integrador_kt.Activitys

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeto_integrador_kt.R
import com.google.firebase.auth.FirebaseAuth
import java.util.Objects

class LoginActivity : BaseActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnEntrar: Button
    private lateinit var txtTelaCadastro: TextView
    private lateinit var fAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        edtEmail = findViewById(R.id.edtEmail)
        edtSenha = findViewById(R.id.edtSenha)
        btnEntrar = findViewById(R.id.btnEntrar)
        txtTelaCadastro = findViewById(R.id.txtTelaCadastro)
        progressBar = findViewById(R.id.progressBar)

        // Instanciar FirebaseAuth
        fAuth = FirebaseAuth.getInstance()

        btnEntrar.setOnClickListener(View.OnClickListener {
            val email = edtEmail.text.toString().trim { it <= ' ' }
            val senha = edtSenha.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty((email))) {
                edtEmail.error = "O campo email é obrigatório"
                return@OnClickListener
            }
            if (TextUtils.isEmpty((senha))) {
                edtSenha.error = "O campo senha é obrigatório"
                return@OnClickListener
            }
            if (senha.length < 6) {
                edtSenha.error = "A senha deve conter no mimimo 6 caracteres"
                return@OnClickListener
            }

            progressBar.visibility = View.VISIBLE

            // authenticate the user
            fAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login efetuado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    progressBar.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Erro!" + Objects.requireNonNull<Exception?>(task.exception).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        txtTelaCadastro.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    UsersActivity::class.java
                )
            )
        }
    }
}