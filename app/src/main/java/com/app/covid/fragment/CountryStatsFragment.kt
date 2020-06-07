package com.app.covid.fragment

import android.os.Bundle
import android.view.*
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
        initializeSearch()
    }

    private fun initializeSearch() {
        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
                    searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            viewModel.addCountryStatsSource(query)
                            searchView.clearFocus()
                            return false
                        }

                        override fun onQueryTextChange(query: String): Boolean {
                            return false
                        }
                    })
                    true
                }
                else -> false
            }
        }
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
        binding.loader.visibility = View.VISIBLE
        viewModel.statsLiveData.observe(viewLifecycleOwner, Observer {
            adapter?.setStatViews(it)
            binding.loader.visibility = View.GONE
            binding.swipeRefresh.isRefreshing = false
        })
    }

    companion object {
        fun newInstance(): CountryStatsFragment {
            return CountryStatsFragment()
        }
    }
}