package com.hans.chuckjokes.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hans.chuckjokes.R
import com.hans.chuckjokes.core.data.source.Resource
import com.hans.chuckjokes.core.domain.model.Jokes
import com.hans.chuckjokes.databinding.ItemRowResultJokesBinding
import java.util.ArrayList

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.ListViewHolder>() {

    private var listData = ArrayList<Jokes>()
    var onItemClick: ((Jokes) -> Unit)? = null

    fun setData(newListData: List<Jokes>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_result_jokes, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowResultJokesBinding.bind(itemView)
        fun bind(data: Jokes) {
            with(binding) {
                tvResultJokes.text = data.value
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}