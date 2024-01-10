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
    var listaVjezbi = ArrayList<VjezbeModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVjezbeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val a : List<VjezbeModel> = snapshot.children.map { dataSnapshot -> dataSnapshot.getValue(
                        VjezbeModel::class.java)!! }
                    val arrayAdapter : ArrayAdapter<VjezbeAdapter> = a as ArrayAdapter<VjezbeAdapter>
                    listaVjezbi.addAll(a)

                }catch (_:Exception){}

                val emptyAdapter = VjezbeAdapter(listaVjezbi,this@VjezbeActivity)
                binding.listaVjezbi.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    adapter = VjezbeAdapter(listaVjezbi, this@VjezbeActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@VjezbeActivity,"Failed With Database", Toast.LENGTH_SHORT).show()
            }
        })

        binding.dodajButton.setOnClickListener {
            intent = Intent(this,DodavanjeVjezbeActivity::class.java)
            startActivity(intent)
        }


    }
}