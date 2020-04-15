package com.example.youtube.ui.videolist.turkey

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtube.common.helper.SingleLiveEvent
import com.example.youtube.model.entities.YouTubeResponseItem
import com.example.youtube.model.rest.ServiceRetrofit
import com.example.youtube.common.helper.Results
import kotlinx.coroutines.launch

class TurkeyViewModel : ViewModel() {

    private val _popListLiveData =
        SingleLiveEvent<Results<YouTubeResponseItem>>()
    val popListLiveData: LiveData<Results<YouTubeResponseItem>>
        get() = _popListLiveData

    fun getPopList(country: String) {
        viewModelScope.launch {
            val data = ServiceRetrofit().getYoutubePopularList(country)
            _popListLiveData.postValue(data)
        }

    }


}