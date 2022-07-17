package com.prabhu.socialmediatest.data

data class MediaResponseData(
    val `data`: List<MediaData>,
    val message: String,
    val statusCode: Int
)