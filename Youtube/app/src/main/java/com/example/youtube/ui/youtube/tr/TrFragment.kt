package com.example.youtube.ui.youtube.tr

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
import com.example.youtube.model.entities.Item
import com.example.youtube.model.entities.YouTubeResponseItem
import com.example.youtube.model.sealed.Results
import com.example.youtube.ui.youtube.YoutubeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tr.popListProgress
import kotlinx.android.synthetic.main.fragment_tr.popListRecyclerView

class TrFragment : Fragment(), OnItemListClick {

    private val viewModel: TrViewModel by viewModels()
    private lateinit var youtubeAdapter: YoutubeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tr, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youtubeAdapter = YoutubeAdapter(emptyList(), lifecycle, this)
        popListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        popListRecyclerView.adapter = youtubeAdapter
        (activity as MainActivity).errorView.setOnClickListener {
            showProgress()
            viewModel.getPopList("TR")
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.popListLiveData.observe(viewLifecycleOwner, Observer { result ->
            handleResponse(result)
        })
        viewModel.getPopList("TR")
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
        val result = value
        youtubeAdapter.updateDataSource(result.items)


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
        val direction = TrFragmentDirections.actionTrFragmentToVideoDetailFragment(clickItem)
        findNavController().navigate(direction)
    }


}