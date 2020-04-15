package com.example.youtube.ui.youtube.tr

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtube.common.SingleLiveEvent
import com.example.youtube.entity.model.YouTubeResponseItem
import com.example.youtube.entity.rest.ServiceRetrofit
import com.example.youtube.entity.sealed.Results
import kotlinx.coroutines.launch

class TrViewModel : ViewModel() {

    private val _popListLiveData = SingleLiveEvent<Results<YouTubeResponseItem>>()
    val popListLiveData: LiveData<Results<YouTubeResponseItem>>
        get() = _popListLiveData

    fun getPopList(country: String) {
        viewModelScope.launch {
            val data = ServiceRetrofit().getYoutubePopularList(country)
            _popListLiveData.postValue(data)
        }

    }


}