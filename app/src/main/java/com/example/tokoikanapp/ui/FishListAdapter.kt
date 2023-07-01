package com.example.tokoikanapp.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tokoikanapp.R
import com.example.tokoikanapp.model.Fish

class FishListAdapter(
    private val onItemClickListener: (Fish) -> Unit
): ListAdapter<Fish, FishListAdapter.FishViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        return FishViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        val fish = getItem(position)
        holder.bind(fish)
        holder.itemView.setOnClickListener{
            onItemClickListener(fish)
        }
    }
    class FishViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView =itemView.findViewById(R.id.nameTextView)
        private val addressTextView: TextView =itemView.findViewById(R.id.addressTextView)
        private val numberTextView: TextView =itemView.findViewById(R.id.numberTextView)

        fun bind(fish: Fish?) {
            nameTextView.text = fish?.name
            addressTextView.text = fish?.address
            numberTextView.text = fish?.number
        }

        companion object {
            fun create(parent: ViewGroup): FishListAdapter.FishViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fish, parent, false)
                return FishViewHolder(view)
            }
        }
    }

    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Fish>(){
            override fun areItemsTheSame(oldItem: Fish, newItem: Fish): Boolean {
               return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Fish, newItem: Fish): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}