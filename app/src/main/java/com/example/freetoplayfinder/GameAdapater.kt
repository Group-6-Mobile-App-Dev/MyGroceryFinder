package com.example.freetoplayfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameAdapter(private val fullList: List<GameItem>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private var displayList: MutableList<GameItem> = fullList.toMutableList()

    private var currentSearch = ""
    private var currentGenre = "All Genres"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.gameImage)
        val gameTitle: TextView = view.findViewById(R.id.gameTitle)
        val gameGenre: TextView = view.findViewById(R.id.gameCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = displayList[position]

        holder.gameTitle.text = game.title
        holder.gameGenre.text = "Genre: ${game.genre}"

        Glide.with(holder.itemView)
            .load(game.thumbnail)
            .centerCrop()
            .into(holder.gameImage)
    }

    override fun getItemCount() = displayList.size


    // i put in the filter function so users can search for whatever game by their fav genre
    private fun applyFilters() {
        val searchLower = currentSearch.lowercase()

        displayList = fullList.filter { item ->

            val matchesSearch = item.title.lowercase().contains(searchLower)

            val matchesGenre =
                currentGenre == "All Genres" ||
                        item.genre.equals(currentGenre, ignoreCase = true)

            matchesSearch && matchesGenre
        }.toMutableList()

        notifyDataSetChanged()
    }

    fun filter(query: String) {
        currentSearch = query
        applyFilters()
    }

    fun filterByGenre(genre: String) {
        currentGenre = genre
        applyFilters()
    }

    // added a sort function to make the app more friendly and interactive
    fun sortByDefault() {
        displayList = fullList.toMutableList()
        applyFilters()
    }

    fun sortByTitleAZ() {
        displayList.sortBy { it.title.lowercase() }
        notifyDataSetChanged()
    }

    fun sortByTitleZA() {
        displayList.sortByDescending { it.title.lowercase() }
        notifyDataSetChanged()
    }

    fun sortByGenreSort() {
        displayList.sortBy { it.genre.lowercase() }
        notifyDataSetChanged()
    }

    fun sortByPublisherSort() {
        displayList.sortBy { it.publisher.lowercase() }
        notifyDataSetChanged()
    }
}
