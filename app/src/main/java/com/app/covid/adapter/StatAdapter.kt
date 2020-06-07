package com.app.covid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.covid.R
import com.app.covid.clickinterface.OnClickListener
import com.app.covid.clickinterface.OnLongClickListener
import com.app.covid.databinding.ViewHolderStatBinding
import com.app.covid.repository.db.model.Stat
import com.app.covid.viewholder.StatViewHolder

class StatAdapter(private val context: Context) :
    RecyclerView.Adapter<StatViewHolder>() {

    private var statViews: MutableList<Stat> = mutableListOf()
    private var onClickListener: OnClickListener<Stat>? = null
    private var onLongClickListener: OnLongClickListener<Stat>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewHolderStatBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.view_holder_stat, parent, false)
        val viewHolder = StatViewHolder(binding)
        viewHolder.onClickListener = onClickListener
        viewHolder.onLongClickListener = onLongClickListener
        return viewHolder
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val statView = statViews[position]

        holder.onBindViewHolder(
            statView
        )
    }

    override fun getItemCount(): Int {
        return statViews.size
    }

    fun setStatViews(newStatViews: List<Stat>?) {
        when {
            newStatViews == null -> return
            statViews.isEmpty() -> {
                statViews.addAll(newStatViews)
                notifyDataSetChanged()
            }
            else -> {
                val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun getOldListSize(): Int {
                        return statViews.size
                    }

                    override fun getNewListSize(): Int {
                        return newStatViews.size
                    }

                    override fun areItemsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {
                        return statViews[oldItemPosition].reference == newStatViews[newItemPosition].reference
                    }

                    override fun areContentsTheSame(
                        oldItemPosition: Int,
                        newItemPosition: Int
                    ): Boolean {

                        val newUser = newStatViews[newItemPosition]
                        val oldUser = statViews[oldItemPosition]
                        return newUser.reference == oldUser.reference
                                && newUser.count == oldUser.count

                    }
                })
                statViews.clear()
                statViews.addAll(newStatViews)

                result.dispatchUpdatesTo(this)
            }
        }
    }
}