package com.intermedia.challenge.ui.events

import android.text.TextUtils.split
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.data.models.Event
import com.intermedia.challenge.databinding.ViewEventItemBinding
import com.intermedia.challenge.ui.comics.ComicsAdapter

class EventsAdapter : com.intermedia.challenge.ui.base.BaseAdapter<Event, EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder =
        EventsViewHolder(
            ViewEventItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    com.intermedia.challenge.R.layout.view_event_item,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val listEvents = list.sortedBy{ it.start }
        holder.bind(listEvents[position])
    }

    class EventsViewHolder(
        private val binding: ViewEventItemBinding,
        private val adapter : ComicsAdapter = ComicsAdapter()
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Event) = with(itemView) {
            var active = false
            var list_comics = item.comics.appearances
            binding.listComicsEvent.adapter = adapter
            binding.event = item
            binding.textDescription.text = setDateStart(item)

            //Si el item es seleccionado, este expande u oculta su respectiva informacion
            binding.root.setOnClickListener {
                //Comprueba si el item, tiene informacion que mostrar
                if(!active && !list_comics.isEmpty()) {
                    binding.listComicsEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                    binding.visibility = true
                    adapter.addAll(list_comics)
                    active = true
                    binding.iconEventState.rotation = 180F
                } else {
                    binding.listComicsEvent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                    binding.visibility = false
                    adapter.clear()
                    active = false
                    binding.iconEventState.rotation = 360F
                }
            }
        }

        fun setDateStart(item: Event): String {
            var date = item.start
            val list = split(date, "-")
            val year = list.get(0)
            val month = list.get(1)
            val rest = list.get(2)
            val rest2 = split(rest, " ")
            val day = rest2.get(0)
            var monthNames = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo","Junio","Julio", "Agosto", "Septiembre", "Octubre", "Noviembre","Diciembre")
            var monthName = monthNames.get(month.toInt()-1)
            val finalDate = "$day de $monthName $year"
            return finalDate
        }
    }
}