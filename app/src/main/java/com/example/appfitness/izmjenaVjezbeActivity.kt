package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.ObjektniModel.VjezbeModel
import com.example.appfitness.databinding.ActivityIzmjenaVjezbeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private var dataBase : DatabaseReference =
    FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Vjezbe")
private var dataBaseIndexi : DatabaseReference =
    FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("IndexVjezbe")

private var popisVjezbi = ArrayList<VjezbeModel>()
private var index : Int = 0

class izmjenaVjezbeActivity : AppCompatActivity() {
    lateinit var binding: ActivityIzmjenaVjezbeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIzmjenaVjezbeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nazadButton.setOnClickListener {
            intent = Intent(this@izmjenaVjezbeActivity,PocetnaActivity::class.java)
            startActivity(intent)
        }

        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a: List<VjezbeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(VjezbeModel::class.java)!! }
                    popisVjezbi.addAll(a)
                }catch (_:Exception){}
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        dataBaseIndexi.child("indexi").addValueEventListener(object : ValueEventListener {
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
            if(binding.imeTxt.text.isEmpty() || binding.brojPonavljanjaTxt.text.isEmpty()||binding.brojSerijaTxt.text.isEmpty()||binding.vrstaVjezbeTxt.text.isEmpty()){
                Toast.makeText(this@izmjenaVjezbeActivity,"Unesi sve!!", Toast.LENGTH_SHORT).show()
            }else{
                popisVjezbi[index].imeVjezbe = binding.imeTxt.text.toString()
                popisVjezbi[index].brojPonavljanja = binding.brojPonavljanjaTxt.text.toString()
                popisVjezbi[index].brojSerija = binding.brojSerijaTxt.text.toString()
                popisVjezbi[index].vrstaVjezbe = binding.vrstaVjezbeTxt.text.toString()
                dataBase.child("${index}").setValue(popisVjezbi[index])
            }

        }

    }
}