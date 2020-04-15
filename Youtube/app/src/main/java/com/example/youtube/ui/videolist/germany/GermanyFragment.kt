package com.example.youtube.ui.videolist.germany

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
import com.example.youtube.ui.videolist.YoutubeAdapter
import kotlinx.android.synthetic.main.fragment_germany.*
import kotlinx.android.synthetic.main.fragment_turkey.popListProgress
import kotlinx.android.synthetic.main.fragment_turkey.popListRecyclerView

class GermanyFragment : Fragment(),
    OnItemListClick {

    private val viewModel: GermanyViewModel by viewModels()
    private lateinit var youtubeAdapter: YoutubeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_germany, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youtubeAdapter = YoutubeAdapter(
            emptyList(),
            lifecycle,
            this
        )
        popListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        popListRecyclerView.adapter = youtubeAdapter

        errorView.setOnClickListener {
            showProgress()
            viewModel.getPopList("TR")
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.popListLiveData.observe(viewLifecycleOwner, Observer { result ->
            handleResponse(result)
        })
        viewModel.getPopList("DE")
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
        val direction =
            GermanyFragmentDirections.actionDeFragmentToVideoDetailFragment(
                clickItem
            )
        findNavController().navigate(direction)
    }

}