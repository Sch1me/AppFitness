package com.example.appfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.ObjektniModel.VjezbeModel
import com.example.appfitness.databinding.OsobeItemBinding
import com.example.appfitness.databinding.VjezbeItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

private val dataBase: DatabaseReference =
    FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Vjezbe")

class VjezbeAdapter(
    private val vjezbeList: ArrayList<VjezbeModel>,
    private val th : Context,
):
    RecyclerView.Adapter<VjezbeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VjezbeAdapter.ViewHolder {
        val v = VjezbeItemBinding.inflate(LayoutInflater.from(th),parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: VjezbeAdapter.ViewHolder, position: Int) {
        holder.bindItem(vjezbeList[position], th)
    }

    override fun getItemCount(): Int {
        return vjezbeList.size
    }

    class ViewHolder(private var itemBinding: VjezbeItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){
        fun bindItem(vjezbeModel: VjezbeModel, th: Context){

            dataBase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        itemBinding.textViewImeVjezbe.text = snapshot.child("imeVjezbe").getValue().toString()
                        itemBinding.textViewBrojSerija.text = snapshot.child("brojSerija").getValue().toString()
                        itemBinding.textViewBrojPonavljanja.text = snapshot.child("brojPonavljanja").getValue().toString()
                        itemBinding.textViewVrstaVjezbe.text = snapshot.child("vrstaVjezbe").getValue().toString()

                    }catch (_: Exception){}

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(th,"Failed with database",Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

}