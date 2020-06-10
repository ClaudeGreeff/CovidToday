package com.app.covid.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
        initializeSearch()
        observeUsers()
    }

    private fun initializeSearch() {
        val searchView = binding.toolBar.menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var handler = Handler()
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                if (query.isEmpty()) {
                    binding.loader.visibility = View.VISIBLE
                    viewModel.addCountryStatsSource("ZA")
                } else {
                    handler.postDelayed({
                        binding.loader.visibility = View.VISIBLE
                        viewModel.addCountryStatsSource(query.capitalize())
                    }, 1000)
                }
                return false
            }
        })
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.loader.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
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
            binding.loader.visibility = View.GONE
        })
    }

    companion object {
        fun newInstance(): CountryStatsFragment {
            return CountryStatsFragment()
        }
    }
}