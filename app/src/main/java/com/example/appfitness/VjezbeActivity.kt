package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appfitness.databinding.ActivityVjezbeBinding

class VjezbeActivity : AppCompatActivity() {
    lateinit var binding : ActivityVjezbeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVjezbeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.dodajButton.setOnClickListener {
            intent = Intent(this,DodavanjeVjezbeActivity::class.java)
            startActivity(intent)
        }


    }
}