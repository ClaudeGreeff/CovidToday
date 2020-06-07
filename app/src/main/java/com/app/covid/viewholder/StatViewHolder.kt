package com.app.covid.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.covid.R
import com.app.covid.clickinterface.OnClickListener
import com.app.covid.clickinterface.OnLongClickListener
import com.app.covid.databinding.ViewHolderStatBinding
import com.app.covid.repository.db.constant.StatType
import com.app.covid.repository.db.model.Stat
import org.koin.core.KoinComponent
import java.text.NumberFormat
import java.util.*

class StatViewHolder(binding: ViewHolderStatBinding) :
    RecyclerView.ViewHolder(binding.root), KoinComponent {
    private val tvTitle = itemView.findViewById<TextView>(R.id.statTitle)
    private val tvCount = itemView.findViewById<TextView>(R.id.statCount)
    private val tvPercentage = itemView.findViewById<TextView>(R.id.statPercentage)

    var onClickListener: OnClickListener<Stat>? = null
    var onLongClickListener: OnLongClickListener<Stat>? = null

    fun onBindViewHolder(stat: Stat) {
        var title = ""
        when(stat.reference){
            StatType.CONFIRMED -> {
                title = "Confirmed Cases"
            }
            StatType.DEATHS -> {
                title = "Total Deaths"
            }
            StatType.RECOVERED -> {
                title = "Total Recovered"
            }
            StatType.DAILY_CONFIRMED -> {
                title = "Daily Confirm"
            }
            StatType.DAILY_DEATHS -> {
                title = "Daily Deaths"
            }
            StatType.CRITICAL -> {
                title = "Total Critical"
            }
            StatType.CONFIRMED_PER_MILLION -> {
                title = "Cases Per Million Population"
            }
            StatType.DEATHS_PER_MILLION -> {
                title = "Deaths Per Million Population"
            }
        }
        tvTitle.text = title
        if(stat.count?.toFloat()!! > 1000000){
            tvCount.text = String.format("%.2fM", stat.count?.toFloat()?.div(1000000.0));
        }else{
            tvCount.text = NumberFormat.getNumberInstance(Locale.getDefault() ).format(stat.count?.toFloat())
        }
    }
}