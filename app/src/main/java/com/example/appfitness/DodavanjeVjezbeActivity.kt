package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appfitness.databinding.ActivityDodavanjeVjezbeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DodavanjeVjezbeActivity : AppCompatActivity() {
    lateinit var binding : ActivityDodavanjeVjezbeBinding
    private val dataBase: DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Vjezbe")

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDodavanjeVjezbeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.dodajButton.setOnClickListener {
            val lista = ArrayList<String>()
            if(binding.imeTxt.text.isEmpty() || binding.brojPonavljanjaTxt.text.isEmpty() || binding.brojSerijaTxt.text.isEmpty() || binding.vrstaVjezbeTxt.text.isEmpty()){
                Toast.makeText(this@DodavanjeVjezbeActivity, "Nisi unio sve",Toast.LENGTH_SHORT).show()
            }else{
                lista.add(binding.imeTxt.text.toString())
                lista.add(binding.brojPonavljanjaTxt.text.toString())
                lista.add(binding.brojSerijaTxt.text.toString())
                lista.add(binding.vrstaVjezbeTxt.text.toString())
                dataBase.push().setValue(lista)
            }


        }
        binding.nazadButton.setOnClickListener {
            intent = Intent(this,OsobeActivity::class.java)
            startActivity(intent)
        }


    }
}