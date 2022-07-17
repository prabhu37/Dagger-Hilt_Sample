package com.prabhu.socialmediatest.api


import com.prabhu.socialmediatest.data.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("select.php")
    suspend fun getMediaList(@Query("lastFetchId") lastId: String): MediaResponseData

    @Multipart
    @POST("uploader.php")
    suspend fun uploadMedia(@Part multipartBody: MultipartBody.Part ): UploadMediaResponse

    @GET("delete.php")
    suspend fun deleteMedia(@Body deleteRequest: DeleteRequest): DeleteResponseData


}