package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.ObjektniModel.VjezbeModel
import com.example.appfitness.databinding.ActivityVjezbeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VjezbeActivity : AppCompatActivity() {
    lateinit var binding : ActivityVjezbeBinding
    private var dataBase : DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Vjezbe")
    private var dataBaseIndexi : DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("IndexVjezbe")
    var listaVjezbi = ArrayList<VjezbeModel>()
    var indexVjezbe : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVjezbeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : List<VjezbeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(VjezbeModel::class.java)!! }
                    listaVjezbi.addAll(a)

                }catch (_:Exception){}
               // val emptyAdapter = VjezbeAdapter(listaVjezbi,this@VjezbeActivity)
                ucitavaRVvjezbi(listaVjezbi)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@VjezbeActivity,"Failed With Database", Toast.LENGTH_SHORT).show()
            }
        })
//ucitava index kliknute osobe
        dataBaseIndexi.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : String = snapshot.child("indexi").getValue().toString()
                    indexVjezbe = a.toInt()
                }catch (e:Exception){
                    Toast.makeText(this@VjezbeActivity,"${e}",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.dodajButton.setOnClickListener {
            intent = Intent(this,DodavanjeVjezbeActivity::class.java)
            startActivity(intent)
        }
        binding.nazadButton.setOnClickListener {
            intent = Intent(this,PocetnaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ucitavaRVvjezbi(popis : ArrayList<VjezbeModel>){
        binding.listaVjezbi.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = VjezbeAdapter(popis, this@VjezbeActivity)
        }
    }
}