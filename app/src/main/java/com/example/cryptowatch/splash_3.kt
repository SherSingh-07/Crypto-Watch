package com.example.cryptowatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptowatch.databinding.ActivitySplash3Binding

class splash_3 : AppCompatActivity() {
    private lateinit var binding: ActivitySplash3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplash3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            startActivity(Intent(this,loginActivity::class.java))
            finish()
        }

    }
}