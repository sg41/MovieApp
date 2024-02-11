package com.example.movieapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize RecyclerView and MovieAdapter
        recyclerView = findViewById(R.id.MovieList)
        movieAdapter = MovieAdapter(getDummyMovies())

        // Set up the RecyclerView with the adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        // Fetch popular movies and update RecyclerView
        getPopularMovies()
    }
    private fun getPopularMovies() {
        val call = RetrofitClient.kinopoiskApiService.getPopularMovies("TOP_POPULAR_MOVIES")
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    movieResponse?.let {
                        val movies = it.items
                        movieAdapter.updateMovies(movies)
                    } ?: showError("Movie response is null")
                } else {
                    // Handle unsuccessful response
                    val errorMessage = "Error: ${response.code()} ${response.message()}"
                    showError(errorMessage)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle network errors
                val errorMessage = "Network error: ${t.message}"
                showError(errorMessage)
            }
        })
    }

    private fun showError(message: String) {
//        showToast(message)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                // Handle button press if needed
            }
            .setCancelable(false)
            .create()

        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
fun getDummyMovies(): List<Movie> {

    // Возвращаем заглушку для примера
    return List(10) { index ->
        Movie(
            kinopoiskId = index,
            nameRu = "Movie $index",
            posterUrl = "https://example.com/poster_$index.jpg",
            year = index.toString(),
            nameEn = "",
            nameOriginal = "",
            countries = emptyList<Country>(),
            genres =  emptyList<Genre>(),
            ratingKinopoisk= 8.8,
            ratingImbd = 8.9,
            type ="String",
            posterUrlPreview = "localhost"
        )
    }
}