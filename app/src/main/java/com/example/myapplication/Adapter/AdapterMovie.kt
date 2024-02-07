package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Movie
import com.example.myapplication.databinding.PartMovieBinding

class AdapterMovie(var collection: MutableList<Movie>): RecyclerView.Adapter<HolderMovie>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovie {
        val inflater = LayoutInflater.from(parent.context)
        var bind = PartMovieBinding.inflate(inflater)
        return  HolderMovie(bind)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: HolderMovie, position: Int) {
        holder.binding.title.text = collection[position].title
        holder.binding.raiting.text = collection[position].ratingImdb.toString()
    }

}

class HolderMovie(var binding: PartMovieBinding): RecyclerView.ViewHolder(binding.root)