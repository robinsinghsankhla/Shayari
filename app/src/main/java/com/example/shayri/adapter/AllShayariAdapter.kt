package com.example.shayri.adapter

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.shayri.CategoryModel
import com.example.shayri.databinding.AllShayariAdapterBinding


class AllShayariAdapter(val context: Context,var list: ArrayList<CategoryModel>): RecyclerView.Adapter<AllShayariAdapter.ViewHolder>() {

    class ViewHolder(val binding: AllShayariAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AllShayariAdapterBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textAll.text = list[position].name.toString()
        holder.binding.btnShare.setOnClickListener {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\n${list[position].name.toString()}\n\n"
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                context.startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        }
        holder.binding.btnCopy.setOnClickListener {
            val clipboard: ClipboardManager? =
                context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("label", list[position].name.toString())
            clipboard?.setPrimaryClip(clip)

            Toast.makeText(context, "Copied Successfull", Toast.LENGTH_SHORT).show()
        }
        holder.binding.btnWhatsapp.setOnClickListener {
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.setType("text/plain")
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, list[position].name.toString())
            try {
                context.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
//                ex.message
            }
        }
    }
}