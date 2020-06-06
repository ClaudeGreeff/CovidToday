package com.app.covid.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.app.covid.R
import com.app.covid.viewmodel.CountryStatsViewModel
import com.app.covid.databinding.FragmentCountryStatsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class CountryStatsFragment: Fragment(){
    private lateinit var binding : FragmentCountryStatsBinding
    private val viewModel: CountryStatsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_stats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        observeUsers()
    }

    private fun observeUsers() {
        viewModel.countryStatsLiveData.observe(viewLifecycleOwner, Observer {
            Timber.i(it.toString())
        })
    }

    companion object {
        fun newInstance(): CountryStatsFragment {
            return CountryStatsFragment()
        }
    }
}