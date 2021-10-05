package com.intermedia.challenge.data.models

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int = 0,
    val title: String = "",
    val comics: Appearances = Appearances(),
    val description: String = "",
    val series: Appearances = Appearances(),
    val start: String = "",
    val end: String = "",
    val stories: Appearances = Appearances(),
    val thumbnail: Thumbnail = Thumbnail(),
    val urls: List<Url> = listOf()
)

data class Aspects(
    val available: Int = 0,
    val collectionURI: String = "",
    @SerializedName("items")
    val appearances: List<Appearance> = listOf(),
    val returned: Int = 0
)



