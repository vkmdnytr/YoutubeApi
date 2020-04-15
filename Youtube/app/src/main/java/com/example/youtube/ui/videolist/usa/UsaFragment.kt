package com.example.youtube.ui.videolist.usa

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
import com.example.youtube.R
import com.example.youtube.common.helper.OnItemListClick
import com.example.youtube.model.entities.Item
import com.example.youtube.model.entities.YouTubeResponseItem
import com.example.youtube.common.helper.Results
import com.example.youtube.ui.videolist.YoutubeTrendRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_usa.*

class UsaFragment : Fragment(),
    OnItemListClick {

    private val viewModel: UsaViewModel by viewModels()
    private lateinit var youtubeAdapter: YoutubeTrendRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        youtubeAdapter = YoutubeTrendRecyclerViewAdapter(
            emptyList(),
            lifecycle,
            this
        )
        val layoutManager = LinearLayoutManager(requireContext())
        popListRecyclerView.layoutManager = layoutManager
        popListRecyclerView.adapter = youtubeAdapter


        errorView.setOnClickListener {
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
        errorView.isVisible = false
        errorView.isClickable = false

    }

    private fun showError(error: String) {
        popListRecyclerView.isVisible = false
        popListProgress.isVisible = false
        errorView.isVisible = true
        errorView.isClickable = true
        errorView.setInfoText(error)
    }

    private fun showProgress() {
        popListRecyclerView.isVisible = false
        popListProgress.isVisible = true
        errorView.isVisible = false
        errorView.isClickable = false
    }

    override fun onItemListClick(clickItem: Item) {
        val direction = UsaFragmentDirections.actionUsFragmentToVideoDetailFragment(clickItem)
        findNavController().navigate(direction)
    }


}