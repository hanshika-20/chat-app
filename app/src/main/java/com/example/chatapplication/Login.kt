package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth=FirebaseAuth.getInstance()//Initialize firebase object
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val email= binding.edtEmail.text.toString()
            val password= binding.edtPassword.text.toString()
            login(email,password)
        }
    }
    private fun login(email:String, password:String){
        if(email=="" || password=="")
        {
            Toast.makeText(this@Login, "Invalid username or password", Toast.LENGTH_LONG).show()
        }
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Code for logging in
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "User does not exist", Toast.LENGTH_LONG).show()
                }
            }
    }
}