package com.river.easyadapterdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.river.easyadapter.EasyAdapter
import com.river.easyadapter.viewholder.EasyViewBindingViewHolder
import com.river.easyadapterdemo.databinding.LayoutItemBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = EasyAdapter()
        adapter.setSpanCount(2)
        adapter.addItem(TitleItem("Title"))
        for (i in 0..10) {
            adapter.addItem(Item("$i"))
        }
        adapter.addItem(TitleItem("Image 1").apply {
            setOnItemClickListener { view, position ->
                Toast.makeText(this@MainActivity, "click Image 1", Toast.LENGTH_SHORT).show()
            }
        })
        for (i in 0..10) {
            adapter.addItem(ImageItem())
        }
        adapter.addItem(TitleItem("Image 2"))
        for (i in 0..10) {
            adapter.addItem(ImageItem())
        }
        recyclerView.adapter = adapter
    }
}