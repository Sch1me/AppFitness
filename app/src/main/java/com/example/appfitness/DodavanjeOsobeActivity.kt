package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.ObjektniModel.VjezbeModel
import com.example.appfitness.databinding.ActivityDodavanjeOsobeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception


class DodavanjeOsobeActivity : AppCompatActivity() {
    lateinit var binding : ActivityDodavanjeOsobeBinding
    private var dataBase : DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Osobe") // imam poseban podjeljak na fb za osobe
                                                                            // trebo bi dodat sve te vrijednosti osobe pod tom jednom osobom tipa osoba1 -> ime,prezime,OIB,godine
    val listaOsoba = ArrayList<OsobeModel>() //lista za osobe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDodavanjeOsobeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : List<OsobeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(
                        OsobeModel::class.java)!! }
                    listaOsoba.addAll(a)

                }catch (_: Exception){}

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DodavanjeOsobeActivity,"Failed With Database",Toast.LENGTH_SHORT).show()
            }
        })


        binding.dodajButton.setOnClickListener {
            if(binding.imeTxt.text.isEmpty() || binding.prezimeTxt.text.isEmpty() || binding.godineTxt.text.isEmpty() || binding.oibTxt.text.isEmpty()){
                Toast.makeText(this@DodavanjeOsobeActivity, "Nisi unio sve",Toast.LENGTH_SHORT).show()
            }else{
                val newOsoba = OsobeModel(binding.imeTxt.text.toString(),binding.prezimeTxt.text.toString(),
                    binding.oibTxt.text.toString(),binding.godineTxt.text.toString())
                listaOsoba.add(newOsoba)
                dataBase.setValue(listaOsoba)
            }

        }

        binding.nazadButton.setOnClickListener {
            intent = Intent(this,OsobeActivity::class.java)
            startActivity(intent)
        }


    }
}

