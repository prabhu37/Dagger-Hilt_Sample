package com.prabhu.socialmediatest.data

data class UploadMediaResponse(
    val `data`: List<Data>,
    val message: String,
    val statusCode: Int
)