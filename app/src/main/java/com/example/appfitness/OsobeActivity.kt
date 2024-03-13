package com.example.appfitness

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.databinding.ActivityOsobeBinding
import com.example.appfitness.databinding.OsobeItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OsobeActivity : AppCompatActivity() {
    lateinit var binding : ActivityOsobeBinding
    private var dataBase : DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Osobe")
    private var dataBaseIndexi : DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("IndexOsoba")

    var listaOsoba = ArrayList<OsobeModel>()
    var listaOsoba2 = ArrayList<OsobeModel>()
    var i : Int = 0
    var indexOsobe : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOsobeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataBase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : List<OsobeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(OsobeModel::class.java)!! }
                    listaOsoba.addAll(a)

                }catch (_:Exception){}
                val emptyAdapter = OsobeAdapter(listaOsoba,this@OsobeActivity)
                ucitavaRV(listaOsoba)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@OsobeActivity,"Failed With Database",Toast.LENGTH_SHORT).show()
            }
        })
//ucitava index kliknute osobe
        dataBaseIndexi.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : String = snapshot.child("indexi").getValue().toString()
                    indexOsobe = a.toInt()
                }catch (e:Exception){
                    Toast.makeText(this@OsobeActivity,"${e}",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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
        binding.izmjeniButton.setOnClickListener {
            intent = Intent(this@OsobeActivity,IzmjenaOsobeActivity::class.java)
            startActivity(intent)
        }

        binding.obrisiButton.setOnClickListener {

            while (i < listaOsoba.size){
                if(i != indexOsobe){
                    listaOsoba2.add(listaOsoba[i])
                }else{

                }
                i++
            }
            dataBase.setValue(listaOsoba2)
            listaOsoba.clear()
            val emptyAdapter = OsobeAdapter(listaOsoba,this@OsobeActivity)
            ucitavaRV(listaOsoba2)
            listaOsoba2.clear()
        }

    }
    private fun ucitavaRV(popis : ArrayList<OsobeModel>){
        binding.listaOsoba.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = OsobeAdapter(popis, this@OsobeActivity)

        }
    }


    }

