package com.intermedia.challenge.ui.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intermedia.challenge.data.models.Event
import com.intermedia.challenge.data.models.NetResult
import com.intermedia.challenge.data.repositories.EventsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events
    var events_list: List<Event> = listOf()
    val eventLimit: Int = 25

    init {
        loadEvents(0)
    }

    private fun loadEvents(offset: Int) {
        viewModelScope.launch {
            when (val response = eventsRepository.getEvents(offset)) {
                is NetResult.Success -> {
                    events_list = getValidEvents(response.data.EventsList.events)
                    _events.postValue(events_list)
                }
                is NetResult.Error -> {
                    Log.e(response.toString(), "Error Get Data")
                    _events.postValue(null)
                }
            }
        }
    }

    private fun getValidEvents(events: List<Event>): List<Event> {
        val today = Calendar.getInstance().time
        val todayDate = SimpleDateFormat("yyyy-M-dd hh:mm:ss").format(today)
        var events_list: MutableList<Event> = mutableListOf()

        events.forEach { item ->

            //Muestra los eventos a partir de la fecha actual
            /*if(item.start != null && item.start >= todayDate) {
                if(events_list.size < eventLimit) {
                    events_list.add(item)
                }
            }*/

            //Muestra todos los eventos
            if(item.start != null) {
                if(events_list.size < eventLimit) {
                    events_list.add(item)
                }
            }
        }
        return events_list
    }

}