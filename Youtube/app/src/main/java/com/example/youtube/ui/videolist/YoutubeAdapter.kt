package com.example.youtube.ui.videolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.R
import com.example.youtube.common.OnItemListClick
import com.example.youtube.model.entities.Item
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView


class YoutubeAdapter(
    private var mDataSource: List<Item>?,
    var lifecycle: Lifecycle,
    var listener: OnItemListClick
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun updateDataSource(dataSource: List<Item>) {
        mDataSource = dataSource
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val youTubePlayerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_youtube, parent, false)
        return YouTubeViewHolder(
            youTubePlayerView
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setBindData(holder, position)
    }

    private fun setBindData(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val youTubeItemViewHolder = holder as YouTubeViewHolder
        val youTubeItem = mDataSource?.get(position)
        youTubeItemViewHolder.subTitle.text = youTubeItem?.snippet?.channelTitle
        youTubeItemViewHolder.title.text = youTubeItem?.snippet?.title
        youTubeItemViewHolder.textReleaseDate.text = youTubeItem?.snippet?.channelId
        youTubeItemViewHolder.likeCount.text = youTubeItem?.statistics?.likeCount
        youTubeItemViewHolder.videoViewCount.text = youTubeItem?.statistics?.viewCount
        val videoId = youTubeItem?.id
        val youtube = youTubeItemViewHolder.youtubePlayView
        youtube.playerUIController.showFullscreenButton(false)
        lifecycle.addObserver(youtube)
        youtube.initialize(
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

        youTubeItemViewHolder.youtubeItemCoverFrameLayout.setOnClickListener {
            youTubeItem?.let { listener.onItemListClick(youTubeItem) }

        }
    }

    override fun getItemCount() = mDataSource?.size ?: 0
    class YouTubeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textReleaseDate: TextView = itemView.findViewById(R.id.textDetailReleaseDate)
        var subTitle: TextView = itemView.findViewById(R.id.textDetailYoutubeSubTitle)
        var title: TextView = itemView.findViewById(R.id.textDetailYoutubeTitle)
        var youtubePlayView: YouTubePlayerView = itemView.findViewById(R.id.youtubeDetailView)
        var likeCount: TextView = itemView.findViewById(R.id.likeDetailCount)
        var videoViewCount: TextView = itemView.findViewById(R.id.videoDetailViewCount)
        var youtubeItemCoverFrameLayout: FrameLayout =
            itemView.findViewById(R.id.youtubeItemCoverFrameLayout)


    }
}