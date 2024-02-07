package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.PartGenrsBinding

class AdapterGenrs(var collection: MutableList<String>) : RecyclerView.Adapter<HolderGenres>() {
    private var listener: Listener? = null
    var lastbut: HolderGenres? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderGenres {
        val inflater = LayoutInflater.from(parent.context)
        val bind = PartGenrsBinding.inflate(inflater)
        return HolderGenres(bind)
    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun onBindViewHolder(holder: HolderGenres, position: Int) {
        holder.binding.button2.text = collection[position]
        holder.binding.button2.setOnClickListener{
            if(listener != null){
                listener!!.Click(holder)
            }
        }
    }
    fun setOnClick(listener: Listener){
        this.listener = listener
    }

    interface Listener{
        fun Click(holder: HolderGenres)
    }
}

class HolderGenres(var binding: PartGenrsBinding) : RecyclerView.ViewHolder(binding.root)