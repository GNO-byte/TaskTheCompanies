package com.gno.taskthecompanies.list

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gno.taskthecompanies.R
import com.gno.taskthecompanies.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    lateinit var listFragmentBinding: ListFragmentBinding
    private lateinit var customRecyclerAdapter: CustomRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //dataBinding
        listFragmentBinding = ListFragmentBinding.inflate(
            inflater, container, false
        )

        return listFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getCompanies()

        initRecyclerVIew()

        listViewModel.companiesLiveData.observe(viewLifecycleOwner, Observer {
            customRecyclerAdapter.submitList(it)
        })

    }

    private fun initRecyclerVIew() {

        val scrollListener = object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount =
                    recyclerView.layoutManager?.childCount
                val totalItemCount = recyclerView.layoutManager?.itemCount
                val firstVisibleItems =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if ((visibleItemCount!! + firstVisibleItems) >= totalItemCount!!) {
                    listViewModel.getCopiesCompanies()
                }
            }
        }

        val listGridSize = resources.getInteger(R.integer.list_grid_size)

        customRecyclerAdapter = CustomRecyclerAdapter(getWidthScreen() / listGridSize) {
            onCellClickListener(it)
        }

        listFragmentBinding.listFragmentRecyclerview.layoutManager =
            GridLayoutManager(activity, listGridSize)
        listFragmentBinding.listFragmentRecyclerview.adapter = customRecyclerAdapter
        listFragmentBinding.listFragmentRecyclerview.addOnScrollListener(scrollListener)

    }

    private fun getWidthScreen(): Int {
        var displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        return displayMetrics.widthPixels
    }

    private fun onCellClickListener(id: String) {
        val bundle = bundleOf("id" to id)
        findNavController().navigate(R.id.action_listFragment_to_cardFragment, bundle)
    }


}