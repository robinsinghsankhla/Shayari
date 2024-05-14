package com.example.shayri

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shayri.adapter.CatAdapter
import com.example.shayri.databinding.ActivityMainBinding
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var menu: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        menu = findViewById(R.id.menu_drawer)
        recyclerView = findViewById(R.id.recycleview_catagary)
        setAdapter(recyclerView)
        menu.setOnClickListener {
            if (binding.main.isDrawerOpen(Gravity.LEFT)){
                binding.main.closeDrawer(Gravity.LEFT)
            }else
                binding.main.openDrawer(Gravity.LEFT)
        }

        binding.nevigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.rate ->{
                    val uri = Uri.parse("market://details?id=$packageName")
                    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(myAppLinkToMarket)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show()
                    }
                    true
                }
                R.id.share->{
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.setType("text/plain")
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage = "\nLet me recommend you this application\n\n"
                        shareMessage =
                            (shareMessage + "https://play.google.com/store/apps/details?id=" + packageName).toString() + "\n\n"
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                    true
                }
                R.id.more->{
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                    }
                    true
                }else->false
            }
        }


    }

    private fun setAdapter(recyclerView: RecyclerView?) {
        recyclerView?.layoutManager = LinearLayoutManager(this)
        val list = arrayListOf<CategoryModel>()
        db = FirebaseFirestore.getInstance()
        db.collection("Shayari").addSnapshotListener(EventListener { value, error ->
            list.clear()
            val data = value?.toObjects(CategoryModel::class.java)
            list.addAll(data!!)
            recyclerView?.adapter = CatAdapter(this,list)
        })
//        var list = arrayListOf("Love Shayari","Romantic shayari","Sad shayari")

    }

    override fun onBackPressed() {
        if (binding.main.isDrawerOpen(Gravity.LEFT)){
            binding.main.closeDrawer(Gravity.LEFT)
        }else
            super.onBackPressed()
    }


}