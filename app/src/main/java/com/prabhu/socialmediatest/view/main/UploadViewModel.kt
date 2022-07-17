package com.prabhu.socialmediatest.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prabhu.socialmediatest.data.UploadMediaResponse
import com.prabhu.socialmediatest.utils.NetworkHelper
import com.prabhu.socialmediatest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class UploadViewModel @Inject  constructor(application: Application,
                                               var uploadRepository: UploadRepository,
                                               var networkHelper: NetworkHelper) : AndroidViewModel(application)  {
    private val uploadResponseLiveData = MutableLiveData<Resource<UploadMediaResponse>>()
    val  mediaUploadStatusLiveData = MutableLiveData<Int>()

     fun uploadMedia(file: File):LiveData<Resource<UploadMediaResponse>> {
        val requestFile  = file.let {
                 var mediaType: MediaType? = null
                 mediaType = "multipart/form-data".toMediaTypeOrNull()!!
                 it.asRequestBody(mediaType)
             }
         val multipartBody = requestFile.let {
             it.let { it1 ->
                 MultipartBody.Part.createFormData(
                     "fileToUpload", file.name,
                     it1
                 )
             }
             }
        viewModelScope.launch(Dispatchers.IO) {
            uploadResponseLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                uploadRepository.uploadMedia(multipartBody).let {
                   if (it.statusCode == 200) {
                        uploadResponseLiveData.postValue(Resource.success(it))
                    } else
                        uploadResponseLiveData.postValue(Resource.error(it.message, null))
                }
            } else uploadResponseLiveData.postValue(Resource.error(networkHelper.networkError(), null))
        }
         return uploadResponseLiveData
    }
}