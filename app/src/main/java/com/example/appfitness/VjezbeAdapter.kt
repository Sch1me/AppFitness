package com.example.appfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appfitness.ObjektniModel.OsobeModel
import com.example.appfitness.ObjektniModel.VjezbeModel
import com.example.appfitness.databinding.OsobeItemBinding
import com.example.appfitness.databinding.VjezbeItemBinding

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
            itemBinding.textViewImeVjezbe.text = vjezbeModel.imeVjezbe
            itemBinding.textViewBrojSerija.text = vjezbeModel.brojSerija
            itemBinding.textViewBrojPonavljanja.text = vjezbeModel.brojPonavljanja
            itemBinding.textViewVrstaVjezbe.text = vjezbeModel.vrstaVjezbe
        }
    }

}