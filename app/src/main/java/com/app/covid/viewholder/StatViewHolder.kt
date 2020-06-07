package com.app.covid.viewholder

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.covid.R
import com.app.covid.clickinterface.OnClickListener
import com.app.covid.clickinterface.OnLongClickListener
import com.app.covid.databinding.ViewHolderStatBinding
import com.app.covid.repository.db.model.Stat
import org.koin.core.KoinComponent

class StatViewHolder(binding: ViewHolderStatBinding) :
    RecyclerView.ViewHolder(binding.root), KoinComponent {
    private val tvTitle = itemView.findViewById<TextView>(R.id.statTitle)
    private val tvCount = itemView.findViewById<TextView>(R.id.statCount)

    var onClickListener: OnClickListener<Stat>? = null
    var onLongClickListener: OnLongClickListener<Stat>? = null

    fun onBindViewHolder(stat: Stat) {
        tvTitle.text = stat.reference?.value
        tvCount.text = stat.count
    }
}