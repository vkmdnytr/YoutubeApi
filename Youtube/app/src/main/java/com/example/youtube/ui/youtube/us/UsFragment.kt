package com.example.youtube.ui.youtube.us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.MainActivity
import com.example.youtube.R
import com.example.youtube.common.OnItemListClick
import com.example.youtube.entity.model.Item
import com.example.youtube.entity.model.YouTubeResponseItem
import com.example.youtube.entity.sealed.Results
import com.example.youtube.ui.youtube.YoutubeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_us.popListProgress
import kotlinx.android.synthetic.main.fragment_us.popListRecyclerView

class UsFragment : Fragment(), OnItemListClick {

    private val viewModel: UsViewModel by viewModels()
    lateinit var youtubeAdapter: YoutubeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        youtubeAdapter = YoutubeAdapter(emptyList(), lifecycle, this)
        val layoutManager = LinearLayoutManager(requireContext())
        popListRecyclerView.layoutManager = layoutManager
        popListRecyclerView.adapter = youtubeAdapter


        (activity as MainActivity).errorView.setOnClickListener {
            showProgress()
            viewModel.getPopList("US")
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.popListLiveData.observe(viewLifecycleOwner, Observer { result ->
            handleResponse(result)
        })
        viewModel.getPopList("US")

    }

    private fun handleResponse(results: Results<YouTubeResponseItem>) {
        showList()
        when (results) {
            is Results.Success -> {
                handleSuccess(results.value)
            }
            is Results.Error -> {
                showError(results.message)
            }
        }
    }

    private fun handleSuccess(value: YouTubeResponseItem) {
        youtubeAdapter.updateDataSource(value.items)


    }

    private fun showList() {
        popListRecyclerView.isVisible = true
        popListProgress.isVisible = false
        (activity as MainActivity).errorView.isVisible = false
        (activity as MainActivity).errorView.isClickable = false

    }

    private fun showError(error: String) {
        popListRecyclerView.isVisible = false
        popListProgress.isVisible = false
        (activity as MainActivity).errorView.isVisible = true
        (activity as MainActivity).errorView.isClickable = true
        (activity as MainActivity).errorView.setInfoText(error)
    }

    private fun showProgress() {
        popListRecyclerView.isVisible = false
        popListProgress.isVisible = true
        (activity as MainActivity).errorView.isVisible = false
        (activity as MainActivity).errorView.isClickable = false
    }

    override fun onItemListClick(clickItem: Item) {
        val direction = UsFragmentDirections.actionUsFragmentToVideoDetailFragment(clickItem)
        findNavController().navigate(direction)
    }


}