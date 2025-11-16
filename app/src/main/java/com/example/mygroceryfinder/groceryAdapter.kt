package com.example.mygroceryfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GroceryAdapter(private val groceryList: List<GroceryItem>) :
    RecyclerView.Adapter<GroceryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val groceryImage: ImageView = view.findViewById(R.id.groceryImage)
        val groceryName: TextView = view.findViewById(R.id.groceryName)
        val groceryCategory: TextView = view.findViewById(R.id.groceryCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grocery = groceryList[position]

        holder.groceryName.text = grocery.name
        holder.groceryCategory.text = "Category: ${grocery.category}"

        // Load image using Glide
        Glide.with(holder.itemView)
            .load(grocery.imageUrl)
            .centerCrop()
            .into(holder.groceryImage)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "You tapped on ${grocery.name}!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = groceryList.size
}
