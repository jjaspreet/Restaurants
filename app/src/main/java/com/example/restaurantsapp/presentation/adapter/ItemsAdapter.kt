package com.example.restaurantsapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsapp.databinding.ItemSearchedBinding
import com.example.restaurantsapp.domain.models.SearchedItem

class ItemsAdapter(
    private val context: Context,
    private val searchedItems: ArrayList<SearchedItem> = ArrayList<SearchedItem>()
) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSearchedBinding.inflate(LayoutInflater.from(context), parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = searchedItems[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    private fun clearList(){
        searchedItems.clear()
    }

    override fun getItemCount(): Int {
        return searchedItems.size
    }

    inner class MyViewHolder(private val binding: ItemSearchedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchedItem) {
            binding.apply {

                titleTextView.text = item.title
                descriptionTextView.text = item.description
            }
        }
    }
}
