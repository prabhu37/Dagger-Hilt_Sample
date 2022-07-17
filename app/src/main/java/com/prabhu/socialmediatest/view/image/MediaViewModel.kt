package com.prabhu.socialmediatest.view.image

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prabhu.socialmediatest.data.MediaResponseData
import com.prabhu.socialmediatest.utils.NetworkHelper
import com.prabhu.socialmediatest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(application: Application, private val imageRepository: MediaRepository,
                                         private val networkHelper: NetworkHelper) : AndroidViewModel(application) {

    private val images = MutableLiveData<Resource<MediaResponseData>>()

     fun fetchImages(lastId: String):LiveData<Resource<MediaResponseData>> {
        viewModelScope.launch {
            images.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                imageRepository.getImages(lastId).let {
                    if (it.statusCode==200) {
                        images.postValue(Resource.success(it))
                    } else
                        images.postValue(Resource.error(it.message, null))
                }
            } else images.postValue(Resource.error(networkHelper.networkError(), null))
        }
         return images
    }
}