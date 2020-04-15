package com.example.youtube.model.rest

import com.example.youtube.common.extensions.safeApiCall
import com.example.youtube.common.helper.Results
import retrofit2.Response

class ServiceRetrofit{
    private val service: ServiceInterface = getService()
    suspend fun getYoutubePopularList(country:String)=
        safeApiCall {
            val response = service.getPopularYouTubeListAsync(regionCode = country).await()
            handleService(response)
        }

    private fun <T:Any>handleService(response:Response<out T>): Results<T> {

        if(response.isSuccessful){
            val response=response.body()
            if(response!=null){
                return Results.Success(response)
            }
        }
        return Results.Error(response.errorBody().toString())
    }

}