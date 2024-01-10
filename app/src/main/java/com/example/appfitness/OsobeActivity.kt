package com.example.appfitness

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.databinding.ActivityOsobeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OsobeActivity : AppCompatActivity() {
    lateinit var binding : ActivityOsobeBinding
    private var dataBase : DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Osobe")
    var listaOsoba = ArrayList<OsobeModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOsobeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataBase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : List<OsobeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(OsobeModel::class.java)!! }
                    val arrayAdapter : ArrayAdapter<OsobeModel> = a as ArrayAdapter<OsobeModel>
                    listaOsoba.addAll(a)

                }catch (_:Exception){}

                val emptyAdapter = OsobeAdapter(listaOsoba,this@OsobeActivity)
                binding.listaOsoba.apply {
                    layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    adapter = OsobeAdapter(listaOsoba, this@OsobeActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@OsobeActivity,"Failed With Database",Toast.LENGTH_SHORT).show()
            }
        })


        binding.nazadButton.setOnClickListener{
            intent = Intent ( this@OsobeActivity,PocetnaActivity::class.java)
            startActivity(intent)
        }
        binding.dodajButton.setOnClickListener {
            intent = Intent(this@OsobeActivity, DodavanjeOsobeActivity::class.java)
            startActivity(intent)
        }

    }
}
