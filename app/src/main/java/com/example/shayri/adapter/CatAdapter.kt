package com.example.shayri.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shayri.AllShayariActivity
import com.example.shayri.CategoryModel
import com.example.shayri.databinding.CatAdapterBinding

class CatAdapter(val context:Context, val list: ArrayList<CategoryModel>) : RecyclerView.Adapter<CatAdapter.ViewHolder>() {



    class ViewHolder(val binding: CatAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CatAdapterBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textCat.text = list[position].name.toString()
        holder.binding.root.setOnClickListener {
            var intent = Intent(context,AllShayariActivity::class.java)
            intent.putExtra("id",list[position].id)
            intent.putExtra("name",list[position].name)
            context.startActivity(intent)
        }
    }
}


