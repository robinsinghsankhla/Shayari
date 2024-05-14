package com.example.shayri

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shayri.adapter.AllShayariAdapter
import com.example.shayri.databinding.ActivityAllShayariBinding
import com.google.firebase.firestore.FirebaseFirestore


class AllShayariActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllShayariBinding
    lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        setAdapter(id,name)
        binding.shayariStatus.text = name.toString()
        binding.backPress.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setAdapter(id: String?, name: String?) {
        val list = arrayListOf<CategoryModel>()
        db = FirebaseFirestore.getInstance()
        db.collection("Shayari").document(id.toString()).collection("All").addSnapshotListener { value, error ->
            val data = value?.toObjects(CategoryModel::class.java)
            list.addAll(data!!)
            binding.allshayariRev.layoutManager = LinearLayoutManager(this)
            binding.allshayariRev.adapter = AllShayariAdapter(this,list)
        }
    }
}