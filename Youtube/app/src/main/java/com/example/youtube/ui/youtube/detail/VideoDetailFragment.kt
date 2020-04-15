package com.example.youtube.ui.youtube.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment

import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.youtube.R

import com.example.youtube.model.entities.Item
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_video_detail.*

class VideoDetailFragment : Fragment() {

    private val viewDetailArg: VideoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_detail, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDataUi()

    }

    private fun setDataUi() {
        val youTubeItem = viewDetailArg.detailItem
        textDetailYoutubeSubTitle.text = youTubeItem.snippet.channelTitle
        textDetailYoutubeTitle.text = youTubeItem.snippet.title
        likeDetailCount.text = youTubeItem.statistics.likeCount
        videoDetailViewCount.text = youTubeItem.statistics.viewCount
        destinationDetailText.text = youTubeItem.snippet.description

        setYoutubeVideoUi(youTubeItem)
    }

    private fun setYoutubeVideoUi(youTubeItem: Item) {
        val videoId = youTubeItem.id
        youtubeDetailView.playerUIController.showFullscreenButton(false)
        lifecycle.addObserver(youtubeDetailView)
        youtubeDetailView.initialize(
            { initializedYouTubePlayer ->
                initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady() {
                        if (videoId != null) {
                            initializedYouTubePlayer.cueVideo(videoId, 0F)
                        }

                    }
                })
            }, true
        )
    }

}