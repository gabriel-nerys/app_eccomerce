package com.example.projeto_integrador_kt.Activitys

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeto_integrador_kt.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UsersActivity : BaseActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtTelefone: EditText
    private lateinit var edtCpf: EditText
    private lateinit var edtCademail: EditText
    private lateinit var edtCadSenha: EditText
    private lateinit var btnCadastro: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var fAuth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private var userID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtNome = findViewById(R.id.edtNome)
        edtTelefone = findViewById(R.id.edtTelefone)
        edtCpf = findViewById(R.id.edtcpf)
        edtCademail = findViewById(R.id.edtCadEmail)
        edtCadSenha = findViewById(R.id.edtCadSenha)
        btnCadastro = findViewById(R.id.btnCadastroLogin)

        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        progressBar = findViewById(R.id.progressBarCad)


        //if (fAuth.getCurrentUser() != null){
        //    startActivity(new Intent(getApplicationContext(), Inicio.class));
        //    finish();
        //}
        btnCadastro.setOnClickListener(View.OnClickListener {
            val nome = edtNome.getText().toString().trim { it <= ' ' }
            val cpf = edtCpf.getText().toString().trim { it <= ' ' }
            val telefone = edtTelefone.getText().toString().trim { it <= ' ' }
            val email = edtCademail.getText().toString().trim { it <= ' ' }
            val senha = edtCadSenha.getText().toString().trim { it <= ' ' }

            if (TextUtils.isEmpty((email))) {
                edtCademail.setError("O campo email é obrigatório")
                return@OnClickListener
            }
            if (TextUtils.isEmpty((senha))) {
                edtCadSenha.setError("O campo senha é obrigatório")
                return@OnClickListener
            }
            if (TextUtils.isEmpty((nome))) {
                edtNome.setError("O campo nome é obrigatório")
                return@OnClickListener
            }
            if (TextUtils.isEmpty((cpf))) {
                edtCpf.setError("O campo CPF é obrigatório")
                return@OnClickListener
            }
            if (TextUtils.isEmpty((telefone))) {
                edtTelefone.setError("O campo telefone é obrigatório")
                return@OnClickListener
            }
            if (senha.length < 6) {
                edtCadSenha.setError("A senha deve conter no mimimo 6 caracteres")
                return@OnClickListener
            }

            progressBar.setVisibility(View.VISIBLE)

            //Registrando usuário no firebase
            fAuth!!.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@UsersActivity,
                        "Usuário Criado!",
                        Toast.LENGTH_SHORT
                    ).show()
                    userID = fAuth!!.currentUser!!.uid
                    val documentReference = fStore!!.collection("users").document(
                        userID!!
                    )
                    val user: MutableMap<String, Any> =
                        HashMap()
                    user["nome"] = nome
                    user["cpf"] = cpf
                    user["telefone"] = telefone
                    user["email"] = email
                    user["senha"] = senha
                    documentReference.set(user).addOnSuccessListener {
                        Log.d(
                            MotionEffect.TAG,
                            "onSuccess: user profile$userID"
                        )
                    }
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@UsersActivity,
                        "Erro!" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}