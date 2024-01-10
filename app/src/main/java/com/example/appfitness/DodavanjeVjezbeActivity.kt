package com.example.appfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.ObjektniModel.VjezbeModel
import com.example.appfitness.databinding.ActivityDodavanjeVjezbeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class DodavanjeVjezbeActivity : AppCompatActivity() {
    lateinit var binding : ActivityDodavanjeVjezbeBinding
    private val dataBase: DatabaseReference =
        FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Vjezbe")
    private val listaVjezbi = ArrayList<VjezbeModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDodavanjeVjezbeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : List<VjezbeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(VjezbeModel::class.java)!! }
                    val arrayAdapter : ArrayAdapter<VjezbeModel> = a as ArrayAdapter<VjezbeModel>
                    listaVjezbi.addAll(a)

                }catch (_: Exception){}

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DodavanjeVjezbeActivity,"Failed With Database",Toast.LENGTH_SHORT).show()
            }
        })

        binding.dodajButton.setOnClickListener {
            if(binding.imeTxt.text.isEmpty() || binding.brojPonavljanjaTxt.text.isEmpty() || binding.brojSerijaTxt.text.isEmpty() || binding.vrstaVjezbeTxt.text.isEmpty()){
                Toast.makeText(this@DodavanjeVjezbeActivity, "Nisi unio sve",Toast.LENGTH_SHORT).show()
            }else{
                val newVjezba = VjezbeModel(binding.imeTxt.text.toString(),binding.brojPonavljanjaTxt.text.toString(),
                    binding.brojSerijaTxt.text.toString(),binding.vrstaVjezbeTxt.text.toString())
                listaVjezbi.add(newVjezba)
                dataBase.setValue(listaVjezbi)
            }


        }
        binding.nazadButton.setOnClickListener {
            intent = Intent(this,OsobeActivity::class.java)
            startActivity(intent)
        }


    }
}