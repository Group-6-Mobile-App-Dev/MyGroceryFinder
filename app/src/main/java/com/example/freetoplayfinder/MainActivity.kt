package com.example.freetoplayfinder

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var rvGroceries: RecyclerView
    private val gameList = mutableListOf<GameItem>()
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvGroceries = findViewById(R.id.grocery_list)
        adapter = GameAdapter(gameList)
        rvGroceries.adapter = adapter
        rvGroceries.layoutManager = LinearLayoutManager(this)

        rvGroceries.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        // Search bar
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }
        })

        fetchGames()
    }

    private fun fetchGames() {
        val client = AsyncHttpClient()
        val url = "https://www.freetogame.com/api/games?platform=pc"

        client.get(url, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val games = json.jsonArray

                    for (i in 0 until games.length()) {
                        val item = games.getJSONObject(i)

                        val title = item.getString("title")
                        val genre = item.getString("genre")
                        val publisher = item.getString("publisher")
                        val thumbnail = item.getString("thumbnail")

                        gameList.add(GameItem(title, genre, publisher, thumbnail))
                    }

                    val genreSet = gameList.map { it.genre }.toSet().sorted()
                    setupGenreSpinner(genreSet)
                    setupSortSpinner()

                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    Log.e("GameList", "Error: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.e("GameList", "Failed: $errorResponse")
            }
        })
    }

    // so this genre spinner will filter the games by genre and the user can select from the range of geners
    private fun setupGenreSpinner(genres: List<String>) {
        val spinner = findViewById<Spinner>(R.id.categorySpinner)

        val items = mutableListOf("All Genres")
        items.addAll(genres)

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            items
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = items[position]
                adapter.filterByGenre(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    // i just recently added this, i felt having a sort option would make the app more interactive
    private fun setupSortSpinner() {
        val spinner = findViewById<Spinner>(R.id.sortSpinner)

        val options = listOf(
            "Sort By: Default",
            "Title (A–Z)",
            "Title (Z–A)",
            "Genre",
            "Publisher"
        )

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            options
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> adapter.sortByDefault()
                    1 -> adapter.sortByTitleAZ()
                    2 -> adapter.sortByTitleZA()
                    3 -> adapter.sortByGenreSort()
                    4 -> adapter.sortByPublisherSort()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
