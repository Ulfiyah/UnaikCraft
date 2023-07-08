package com.example.unaikcraft.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unaikcraft.R
import com.example.unaikcraft.model.CraftModel


class UnaikListAdapter(
    private val onItemClickListener: (CraftModel) -> Unit
) : ListAdapter<CraftModel, UnaikListAdapter.UnaikViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnaikViewHolder{
        return UnaikViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder: UnaikViewHolder, position: Int) {
        val craftModel = getItem (position)
        holder.bind(craftModel)
        holder.itemView.setOnClickListener{
            onItemClickListener(craftModel)
        }
    }
    class UnaikViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val namaTV: TextView = itemView.findViewById(R.id.namaTV)
        private val alamatTV: TextView = itemView.findViewById(R.id.alamatTV)
        private val nohpTV: TextView = itemView.findViewById(R.id.nohpTV)

        fun bind(craftModel: CraftModel?) {
            namaTV.text = craftModel?.nama
            alamatTV.text = craftModel?.alamat
            nohpTV.text = craftModel?.nohp

        }

        companion object {
            fun create(parent: ViewGroup): UnaikListAdapter.UnaikViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_unaik, parent, false)
                return UnaikViewHolder(view)

            }
        }
    }
    companion object {
        private val WORDS_COMPARATOR = object  : DiffUtil.ItemCallback<CraftModel>() {
            override fun areItemsTheSame(oldItem: CraftModel, newItem: CraftModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CraftModel, newItem: CraftModel): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}
