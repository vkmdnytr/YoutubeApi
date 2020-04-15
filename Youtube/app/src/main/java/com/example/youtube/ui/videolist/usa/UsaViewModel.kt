package com.example.youtube.ui.videolist.usa

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtube.common.helper.SingleLiveEvent
import com.example.youtube.model.entities.YouTubeResponseItem
import com.example.youtube.model.rest.YoutubeRepository
import com.example.youtube.common.helper.Results
import com.example.youtube.model.rest.YoutubeRepository.getYoutubePopularList
import kotlinx.coroutines.launch

class UsaViewModel : ViewModel() {

    private val _popListLiveData =
        SingleLiveEvent<Results<YouTubeResponseItem>>()
    val popListLiveData: LiveData<Results<YouTubeResponseItem>>
        get() = _popListLiveData

    fun getPopList(country: String) {
        viewModelScope.launch {
            val data = getYoutubePopularList(country)
            _popListLiveData.postValue(data)
        }

    }

}