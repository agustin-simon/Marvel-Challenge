package com.intermedia.challenge.data.repositories

import com.intermedia.challenge.data.models.NetResult
import com.intermedia.challenge.data.services.EventService
import com.intermedia.challenge.data.services.EventsResponse

class EventsRepository(
    private val eventsService : EventService
): BaseRepository() {

    suspend fun getEvents(offset: Int, limit: Int = 80): NetResult<EventsResponse> =
        handleResult(eventsService.getEvents(authParams.getMap(), offset, limit))
}