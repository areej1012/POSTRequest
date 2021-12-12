package com.example.postrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postrequest.databinding.ItemRowBinding

class RecycleViewAdpter(private var list: ArrayList<PostItem>): RecyclerView.Adapter<RecycleViewAdpter.ItemRowHolder>() {
    class ItemRowHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        return ItemRowHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        var listItem = list
        holder.binding.apply {
            tvName.text = listItem[position].name
            tvLocation.text = "    ${listItem[position].location}"
        }
    }

    override fun getItemCount(): Int = list.size
}