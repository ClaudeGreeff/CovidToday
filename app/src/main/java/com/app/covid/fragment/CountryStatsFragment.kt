package com.app.covid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
import com.app.covid.viewmodel.CountryStatsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CountryStatsFragment : Fragment() {
    private lateinit var binding: FragmentCountryStatsBinding
    private val viewModel: CountryStatsViewModel by sharedViewModel()
    private var adapter: StatAdapter? = null

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
        setupRecyclerView()
        setupSwipeToRefresh()
        observeUsers()
        initializeEditText()
    }

    private fun initializeEditText() {
        binding.etCountryCode.addTextChangedListener(object : TextWatcher {
            var handler = Handler()
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("DefaultLocale")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handler.removeCallbacksAndMessages(null)
                if (s?.length == 0) {
                    viewModel.addCountryStatsSource("ZA")
                } else {
                    handler.postDelayed({
                        viewModel.addCountryStatsSource(s.toString().capitalize())
                    }, 2000)
                }
            }

        })
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupRecyclerView() {
        adapter = StatAdapter(requireActivity())
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter
    }

    private fun observeUsers() {
        viewModel.statsLiveData.observe(viewLifecycleOwner, Observer {
            adapter?.setStatViews(it)
            binding.swipeRefresh.isRefreshing = false
        })
    }

    companion object {
        fun newInstance(): CountryStatsFragment {
            return CountryStatsFragment()
        }
    }
}