package com.example.mygroceryfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameAdapter(private val gameList: List<GameItem>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.gameImage)
        val gameTitle: TextView = view.findViewById(R.id.gameTitle)
        val gameCategory: TextView = view.findViewById(R.id.gameCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gameList[position]

        holder.gameTitle.text = game.title
        holder.gameCategory.text = "Category: ${game.genre}"

        Glide.with(holder.itemView)
            .load(game.thumbnail)
            .centerCrop()
            .into(holder.gameImage)

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "You tapped on ${game.title}!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = gameList.size
}
