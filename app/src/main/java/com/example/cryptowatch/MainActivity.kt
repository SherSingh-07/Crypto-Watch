package com.example.cryptowatch

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cryptowatch.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager : FragmentManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()


        goToFragment(fragment_home())

        binding.bn.setOnItemSelectedListener {
            if(it.itemId == R.id.fragment_Home) {
                goToFragment(fragment_home())
            }
            if(it.itemId == R.id.fragment_Market) {
                goToFragment(fragment_market())
            }
            if(it.itemId == R.id.Favourite_button) {
                goToFragment(watchlistFragment())
            }


            return@setOnItemSelectedListener true
        }
//        binding.bn.setOnNavigationItemSelectedListener {
//            menu ->
//            when(menu.itemId){
//                R.id.Favourite_button -> {
//                    val intent = Intent(this,watchlist_activity::class.java)
//                    startActivity(intent)
//                    true
//                }
//
//                else -> false
//            }
//        }



    }

    private fun goToFragment(fragment: Fragment){
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
    }


}