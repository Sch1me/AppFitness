package com.example.appfitness


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appfitness.databinding.ActivityPocetnaBinding


class PocetnaActivity : AppCompatActivity() {
    lateinit var binding : ActivityPocetnaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPocetnaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.osobeButton.setOnClickListener {
            intent = Intent(this,OsobeActivity::class.java)
            startActivity(intent)
        }
        binding.vjezbeButton.setOnClickListener {
            intent = Intent(this,VjezbeActivity::class.java)
            startActivity(intent)
        }


    }
}