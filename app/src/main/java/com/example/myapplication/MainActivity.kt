package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.API.API_Connection
import com.example.myapplication.Adapter.AdapterGenrs
import com.example.myapplication.Adapter.AdapterMovie
import com.example.myapplication.Adapter.HolderGenres
import com.example.myapplication.Data.Movie
import com.example.myapplication.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var listMovie: MutableList<Movie> = mutableListOf()
    private var plistMovie: MutableList<Movie> = mutableListOf()
    private var listGenrs: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getApi()
        binding.sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.length >= 3){
                    plistMovie.clear()
                    for(movie in listMovie){
                        if(movie.title!!.contains(newText, true)){
                            plistMovie.add(movie)
                        }
                    }
                    showMovie(plistMovie)
                }
                return true
            }
        })
    }

    private fun getApi(){
        API_Connection.getConnection()
            .GetMovies()
            .enqueue(object :
                Callback<MutableList<Movie>> {
                override fun onResponse(
                    call: Call<MutableList<Movie>>,
                    response: Response<MutableList<Movie>>
                ) {
                    if (response.code() == 200) {
                        listMovie = response.body()!!.toMutableList()
                        plistMovie = response.body()!!.toMutableList()
                        for (movie in listMovie){
                            for (genr in movie.genres){
                                if (!listGenrs.contains(genr)){
                                    listGenrs.add(genr)
                                }
                            }
                        }
                        showMovie(listMovie)
                        showGenrs(listGenrs)
                    } else {
                        Log.println(Log.ERROR, "API", "Error connection")
                        Toast.makeText(
                            this@MainActivity,
                            "Error connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MutableList<Movie>>, t: Throwable) {
                    Log.println(Log.ERROR, "API", t.message.toString())
                    Toast.makeText(this@MainActivity, "Error request", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun showGenrs(collection: MutableList<String>){
        val adapter: AdapterGenrs = AdapterGenrs(collection)
        binding.rvGenrs.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvGenrs.adapter = adapter
        adapter.setOnClick(object: AdapterGenrs.Listener{
            override fun Click(holder: HolderGenres) {
                plistMovie.clear()
                for (movie in listMovie){
                    for (genr in movie.genres){
                        if (holder.binding.button2.text == genr && !plistMovie.contains(movie)){
                            plistMovie.add(movie)
                        }
                    }
                }
                showMovie(plistMovie)
            }
        })
    }
    private fun showMovie(collection: MutableList<Movie>){
        binding.rvMove.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvMove.adapter = AdapterMovie(collection)
    }
}
