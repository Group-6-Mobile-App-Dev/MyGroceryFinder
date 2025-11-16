package com.example.mygroceryfinder

import android.os.Bundle
import android.util.Log
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

        // Initialize RecyclerView
        rvGroceries = findViewById(R.id.grocery_list)
        adapter = GameAdapter(gameList)
        rvGroceries.adapter = adapter
        rvGroceries.layoutManager = LinearLayoutManager(this)

        // Optional: Add dividers between list items
        rvGroceries.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

        // Fetch game data from API
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

                    adapter.notifyDataSetChanged()
                    Log.d("GameList", "Loaded games successfully! (${gameList.size} items)")

                } catch (e: Exception) {
                    Log.e("GameList", "Error parsing JSON: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.e("GameList", "Failed to load games: $errorResponse")
            }
        })
    }
}
