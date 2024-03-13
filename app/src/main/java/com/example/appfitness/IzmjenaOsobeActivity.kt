package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.databinding.ActivityIzmjenaOsobeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private var dataBase : DatabaseReference =
    FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Osobe")
private var dataBaseIndexi : DatabaseReference =
    FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("IndexOsoba")

private var popisOsobe = ArrayList<OsobeModel>()
private var index : Int = 0

class IzmjenaOsobeActivity : AppCompatActivity() {
    lateinit var binding: ActivityIzmjenaOsobeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIzmjenaOsobeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nazadButton.setOnClickListener {
            intent = Intent(this@IzmjenaOsobeActivity,PocetnaActivity::class.java)
            startActivity(intent)
        }

        dataBase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a: List<OsobeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(OsobeModel::class.java)!! }
                    popisOsobe.addAll(a)
                }catch (_:Exception){}
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        dataBaseIndexi.child("indexi").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a: String = snapshot.getValue().toString()
                    index = a.toInt()
                }catch (_:Exception){}
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.dodajButton.setOnClickListener {
            if(binding.imeTxt.text.isEmpty() || binding.prezimeTxt.text.isEmpty()||binding.godineTxt.text.isEmpty()||binding.oibTxt.text.isEmpty()){
                Toast.makeText(this@IzmjenaOsobeActivity,"Unesi sve!!",Toast.LENGTH_SHORT).show()
            }else{
                popisOsobe[index].ime = binding.imeTxt.text.toString()
                popisOsobe[index].prezime = binding.prezimeTxt.text.toString()
                popisOsobe[index].OIB = binding.oibTxt.text.toString()
                popisOsobe[index].godine = binding.godineTxt.text.toString()
                dataBase.child("${index}").setValue(popisOsobe[index])
            }

        }

    }
}