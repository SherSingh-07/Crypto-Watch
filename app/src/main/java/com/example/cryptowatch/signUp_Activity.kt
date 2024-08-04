package com.example.cryptowatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cryptowatch.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signUp_Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        val uid= firebaseAuth.currentUser?.uid
        databaseReference= FirebaseDatabase.getInstance().getReference("users")
        binding.button2.setOnClickListener {
            var firstName= binding.editTextText.text.toString()
            val lastName= binding.editTextText2.text.toString()
            val user = users(firstName,lastName)
            if (uid !=null){

                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){

                    }
                }
            }

        }

        binding.textView10.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
            finish()
        }
            binding.button2.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
                binding.imageButton.setOnClickListener {
                    startActivity(Intent(this,loginActivity::class.java))
                    finish()
        }

               binding.button2.setOnClickListener {
                   signUpUser()
               }
    }

       private fun signUpUser(){
           val email = binding.editTextTextEmailAddress2.text.toString()
           val password = binding.editTextTextPassword2.text.toString()
           val confirmPassword  = binding.editTextTextPassword3.text.toString()
           val name = binding.editTextText.text.toString()
           val lastName = binding.editTextText2.text.toString()


           if(email.isBlank() && password.isBlank() && confirmPassword.isBlank() && name.isBlank() && lastName.isBlank()){
               Toast.makeText(this,"All fields can't be blank",Toast.LENGTH_SHORT).show()
               return
           }
           if(email.isBlank()){
               Toast.makeText(this," Enter your email",Toast.LENGTH_SHORT).show()
               return
           }
           if(name.isBlank()){
               Toast.makeText(this," Enter your name",Toast.LENGTH_SHORT).show()
               return
           }
           if(lastName.isBlank()){
               Toast.makeText(this," Enter your last name",Toast.LENGTH_SHORT).show()
               return
           }
           if(password.isBlank()){
               Toast.makeText(this," Enter your password",Toast.LENGTH_SHORT).show()
               return
           }
           if(confirmPassword.isBlank()){
               Toast.makeText(this," Enter your confirm password",Toast.LENGTH_SHORT).show()
               return
           }
           if (password != confirmPassword){
               Toast.makeText(this," Password and confirm password do not match",Toast.LENGTH_SHORT).show()
               return
           }

           firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
             if (it.isSuccessful){
                 Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                 val intent = Intent(this,MainActivity::class.java)
                 startActivity(intent)
                 finish()
             }
               else{
                   Toast.makeText(this,"Error creating user",Toast.LENGTH_SHORT).show()
             }
           }

       }

}