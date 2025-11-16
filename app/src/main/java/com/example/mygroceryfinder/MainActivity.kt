package com.example.mygroceryfinder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvGroceries: RecyclerView
    private val groceryList = mutableListOf<GroceryItem>()
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        rvGroceries = findViewById(R.id.grocery_list)
        adapter = GroceryAdapter(groceryList)
        rvGroceries.adapter = adapter
        rvGroceries.layoutManager = LinearLayoutManager(this)


        rvGroceries.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )


        loadSampleGroceries()
    }

    private fun loadSampleGroceries() {
        val sampleItems = listOf(
            GroceryItem("Milk", "Dairy", "https://upload.wikimedia.org/wikipedia/commons/4/4c/Milk_glass.jpg"),
            GroceryItem("Eggs", "Dairy", "https://upload.wikimedia.org/wikipedia/commons/7/7b/Eggs_carton.jpg"),
            GroceryItem("Apples", "Produce", "https://upload.wikimedia.org/wikipedia/commons/1/15/Red_Apple.jpg"),
            GroceryItem("Bread", "Bakery", "https://upload.wikimedia.org/wikipedia/commons/d/d3/Freshly_baked_bread.jpg")
        )

        groceryList.addAll(sampleItems)
        adapter.notifyDataSetChanged()

        Log.d("GroceryList", "Loaded sample groceries successfully!")
    }
}
