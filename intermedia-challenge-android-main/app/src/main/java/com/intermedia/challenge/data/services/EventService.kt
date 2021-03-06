package com.intermedia.challenge.data.services

import com.google.gson.annotations.SerializedName
import com.intermedia.challenge.data.models.Event
import retrofit2.Response
import retrofit2.http.*

interface EventService {

    @GET("events")
    suspend fun getEvents(
        @QueryMap auth: HashMap<String, String>,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<EventsResponse>
}

data class EventsResponse(
    val code: Int = 0,
    @SerializedName("data")
    val EventsList: EventsList
)

data class EventsList(
    @SerializedName("results")
    val events: List<Event>
)