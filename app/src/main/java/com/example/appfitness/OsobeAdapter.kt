package com.example.appfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.databinding.OsobeItemBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private var dataBase : DatabaseReference =
    FirebaseDatabase.getInstance("https://appfitness-68156-default-rtdb.europe-west1.firebasedatabase.app/").getReference("IndexOsoba")
class OsobeAdapter (
    private val osobeList: ArrayList<OsobeModel>,
    private val th : Context,
) :
    RecyclerView.Adapter<OsobeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OsobeAdapter.ViewHolder {
        val v = OsobeItemBinding.inflate(LayoutInflater.from(th),parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: OsobeAdapter.ViewHolder, position: Int) {
        holder.bindItem(osobeList[position], th, position)

    }

    override fun getItemCount(): Int {
        return osobeList.size
    }

    class ViewHolder(private var itemBinding: OsobeItemBinding):
            RecyclerView.ViewHolder(itemBinding.root){
                fun bindItem(osobeModel:OsobeModel, th: Context, position: Int){
                    itemBinding.textViewIme.text = osobeModel.ime
                    itemBinding.textViewPrezime.text = osobeModel.prezime
                    itemBinding.textViewGodine.text = osobeModel.godine
                    itemBinding.textViewOIB.text = osobeModel.OIB
                    itemBinding.root.setOnClickListener {
                    dataBase.child("indexi").setValue(position)
                    }
                }
            }

}