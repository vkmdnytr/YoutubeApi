package com.example.youtube.ui.videolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.R
import com.example.youtube.common.helper.OnItemListClick
import com.example.youtube.model.entities.Item
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView


class YoutubeTrendRecyclerViewAdapter(
    private var dataSource: List<Item>,
    var lifecycle: Lifecycle,
    var listener: OnItemListClick
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun updateDataSource(dataSource: List<Item>) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val youTubePlayerView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_youtube, parent, false)
        return YouTubeViewHolder(youTubePlayerView, lifecycle, listener)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val youtubeHolder = holder as YouTubeViewHolder
        val videoItem = dataSource[position]
        youtubeHolder.bindData(videoItem)
    }

    override fun getItemCount() = dataSource.size


    class YouTubeViewHolder(
        itemView: View,
        val lifecycle: Lifecycle,
        private val listener: OnItemListClick
    ) : RecyclerView.ViewHolder(itemView) {
        private var textReleaseDate: TextView = itemView.findViewById(R.id.textDetailReleaseDate)
        private var subTitle: TextView = itemView.findViewById(R.id.textDetailYoutubeSubTitle)
        private var title: TextView = itemView.findViewById(R.id.textDetailYoutubeTitle)
        private var youtubePlayView: YouTubePlayerView =
            itemView.findViewById(R.id.youtubeDetailView)
        private var likeCount: TextView = itemView.findViewById(R.id.likeDetailCount)
        var videoViewCount: TextView = itemView.findViewById(R.id.videoDetailViewCount)
        var youtubeItemCoverFrameLayout: FrameLayout =
            itemView.findViewById(R.id.youtubeItemCoverFrameLayout)

        fun bindData(
            mDataSource: Item
        ) {

            subTitle.text = mDataSource.snippet.channelTitle
            title.text = mDataSource.snippet.title
            textReleaseDate.text = mDataSource.snippet.channelId
            likeCount.text = mDataSource.statistics.likeCount
            videoViewCount.text = mDataSource.statistics.viewCount



            youtubePlayView.playerUIController.showFullscreenButton(false)
            lifecycle.addObserver(youtubePlayView)

            youtubePlayView.initialize(
                { initializedYouTubePlayer ->
                    initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady() {

                            initializedYouTubePlayer.cueVideo(mDataSource.id, 0F)


                        }
                    })
                }, true
            )

            youtubeItemCoverFrameLayout.setOnClickListener {
                mDataSource.let { listener.onItemListClick(mDataSource) }

            }
        }


    }
}