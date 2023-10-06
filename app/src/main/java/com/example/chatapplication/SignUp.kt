package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapplication.databinding.ActivityLoginBinding
import com.example.chatapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener{
            val name=binding.edtName.text.toString()
            val email=binding.edtEmail.text.toString()
            val password=binding.edtPassword.text.toString()
            signUp(name,email,password)
        }
    }
    private fun signUp(name:String,email: String,password:String)
    {
        if(email=="" || password=="")
        {
            Toast.makeText(this@SignUp, "Invalid username or password", Toast.LENGTH_LONG).show()
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home
                    addUserToDatabase(name, email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Some error occured", Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun addUserToDatabase(name:String,email: String,uid:String?)
    {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(name,email,uid))
        }
    }
}