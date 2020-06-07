package com.app.covid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.covid.R
import com.app.covid.adapter.StatAdapter
import com.app.covid.databinding.FragmentCountryStatsBinding
import com.app.covid.repository.db.constant.StatType
import com.app.covid.repository.db.model.Stat
import com.app.covid.viewmodel.CountryStatsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class CountryStatsFragment : Fragment() {
    private lateinit var binding: FragmentCountryStatsBinding
    private val viewModel: CountryStatsViewModel by sharedViewModel()
    private val adapter: StatAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country_stats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupRecyclerView();
        observeUsers()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter
    }

    private fun observeUsers() {
        viewModel.countryStatsLiveData.observe(viewLifecycleOwner, Observer {
            val statsList = mutableListOf<Stat>()

            if(!it.totalConfirmed.isNullOrBlank()){
                val stat = Stat(StatType.CONFIRMED, it.totalConfirmed)
                statsList.add(stat)
            }
            if(!it.totalDeaths.isNullOrBlank()){
                val stat = Stat(StatType.DEATHS, it.totalDeaths)
                statsList.add(stat)
            }
            if(!it.totalRecovered.isNullOrBlank()){
                val stat = Stat(StatType.RECOVERED, it.totalRecovered)
                statsList.add(stat)
            }

            adapter?.setStatViews(statsList)
        })
    }

    companion object {
        fun newInstance(): CountryStatsFragment {
            return CountryStatsFragment()
        }
    }
}