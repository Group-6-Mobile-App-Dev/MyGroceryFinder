package com.example.freetoplayfinder

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toolbar = findViewById<Toolbar>(R.id.detailToolbar)
        setSupportActionBar(toolbar)

        // this will let you go back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


        toolbar.setNavigationOnClickListener {
            finish()
        }

        val title = intent.getStringExtra("title")
        val genre = intent.getStringExtra("genre")
        val publisher = intent.getStringExtra("publisher")
        val thumbnail = intent.getStringExtra("thumbnail")
        val description = intent.getStringExtra("description")

        val image = findViewById<ImageView>(R.id.detailImage)
        val titleView = findViewById<TextView>(R.id.detailTitle)
        val genreView = findViewById<TextView>(R.id.detailGenre)
        val publisherView = findViewById<TextView>(R.id.detailPublisher)
        val descView = findViewById<TextView>(R.id.detailDescription)

        titleView.text = title
        genreView.text = "Genre: $genre"
        publisherView.text = "Publisher: $publisher"
        descView.text = description

        Glide.with(this).load(thumbnail).into(image)
    }
}
