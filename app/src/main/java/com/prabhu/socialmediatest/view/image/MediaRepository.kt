package com.prabhu.socialmediatest.view.image

import com.prabhu.socialmediatest.api.ApiService

import javax.inject.Inject

class MediaRepository @Inject constructor(val api: ApiService){

    suspend fun getImages(lastPosId: String) =  api.getMediaList(lastPosId)
}