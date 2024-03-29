package com.example.movies
import com.example.movieapp.MovieDetails
import com.example.movieapp.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApiService {

    @GET("/api/v2.2/films/collections")
    fun getPopularMovies(@Query("type") type: String): Call<MovieResponse>

    @GET("/api/v2.2/films/{id}")
    fun getMovieDetails(@Path("id") type: Int): Call<MovieDetails>
}