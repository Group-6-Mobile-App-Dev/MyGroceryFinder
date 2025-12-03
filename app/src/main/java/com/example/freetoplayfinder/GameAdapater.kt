package com.example.freetoplayfinder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameAdapter(private val fullList: List<GameItem>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    // displayList is a mutable copy of the fullList used to apply searches, filtering, and sorting
    // It is the final list of GameItems that are displayed to users in the app
    private var displayList: MutableList<GameItem> = fullList.toMutableList()
    // These are updated with user selection when a search is typed or genre picked
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


        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val intent = Intent(ctx, DetailsActivity::class.java)

            intent.putExtra("title", game.title)
            intent.putExtra("genre", game.genre)
            intent.putExtra("publisher", game.publisher)
            intent.putExtra("thumbnail", game.thumbnail)


            intent.putExtra("description", game.shortDescription ?: "No description available.")

            ctx.startActivity(intent)
        }
    }

    override fun getItemCount() = displayList.size


    // the inital filter section

    private fun applyFilters() {
        // set query to lower case
        val searchLower = currentSearch.lowercase()

        displayList = fullList.filter { item ->
            // check if game title contains the searched word(s)
            val matchesSearch = item.title.lowercase().contains(searchLower)
            // if all genres, accept all games, othwerwise only currentGenre
            val matchesGenre =
                currentGenre == "All Genres" ||
                        item.genre.equals(currentGenre, ignoreCase = true)

            // accepts item if both true, and replaces to displayList
            matchesSearch && matchesGenre
        }.toMutableList()

        // ReyclerView UI is refreshed
        notifyDataSetChanged()
    }

    // Applys filters and generates displayList when text is searched
    fun filter(query: String) {
        currentSearch = query
        applyFilters()
    }

    // Applys filters and generates displayList when genre selected
    fun filterByGenre(genre: String) {
        currentGenre = genre
        applyFilters()
    }


    // so this was the sort section i added because i felt the sorting only by genere was lame

    fun resetSort() {
        displayList = fullList.toMutableList()
        applyFilters()
    }

    fun sortByTitleAZ() {
        displayList.sortBy { it.title }
        notifyDataSetChanged()
    }

    fun sortByTitleZA() {
        displayList.sortByDescending { it.title }
        notifyDataSetChanged()
    }

    fun sortByGenreSort() {
        displayList.sortBy { it.genre }
        notifyDataSetChanged()
    }

    fun sortByPublisherSort() {
        displayList.sortBy { it.publisher }
        notifyDataSetChanged()
    }
}
