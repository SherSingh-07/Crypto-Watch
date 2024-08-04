package com.example.cryptowatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cryptowatch.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.zip.Inflater

class loginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()



        binding.textView9.setOnClickListener {
            startActivity(Intent(this,signUp_Activity::class.java))
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            //finish()
        }

        binding.button2.setOnClickListener {
            login()
        }

    }

    private fun login(){
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()

        if(email.isBlank() && password.isBlank()){
            Toast.makeText(this," Enter your email and password",Toast.LENGTH_SHORT).show()
            return
        }
        if(email.isBlank()){
            Toast.makeText(this," Enter your email",Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isBlank()){
            Toast.makeText(this," Enter your password",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
                    finish()
            }
            else{
                Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }


}