package com.boa.reigntest.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewGroupCompat
import androidx.recyclerview.widget.RecyclerView
import com.boa.reigntest.R
import com.boa.reigntest.base.OnSelectItem
import java.lang.ref.WeakReference

class ListAdapter<T>(
    private val context: WeakReference<Context>,
    private val onSelectItem: OnSelectItem<T>
) :
    RecyclerView.Adapter<ListViewHolder>() {
    private var list = listOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(context.get()).inflate(
            R.layout.item_list,
            parent,
            false
        )
        ViewGroupCompat.setTransitionGroup(view as ViewGroup, true)
        val holder = ListViewHolder(view)

        holder.itemRoot.setOnClickListener {
            onSelectItem.onSelectItem(list[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        list[position]
    }

    fun setData(newList: List<T>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}