package com.prabhu.socialmediatest.view.main

import com.prabhu.socialmediatest.api.ApiService
import okhttp3.MultipartBody

import javax.inject.Inject

class UploadRepository @Inject constructor(private val api: ApiService){

    suspend fun uploadMedia(multipartBody: MultipartBody.Part) =  api.uploadMedia(multipartBody)
}