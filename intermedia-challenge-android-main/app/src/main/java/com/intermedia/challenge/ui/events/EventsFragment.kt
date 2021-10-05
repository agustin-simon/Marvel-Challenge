package com.intermedia.challenge.ui.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.intermedia.challenge.R
import com.intermedia.challenge.databinding.FragmentEventsBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel


class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val viewModel: EventsViewModel by sharedViewModel()
    private val adapter = EventsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventsList()
    }

    private fun setupEventsList() {
        binding.listEvents.adapter = adapter
        viewModel.events.observe(viewLifecycleOwner, { events ->
            if(events != null) {
                adapter.update(events)
            } else {
                val error = "The data could not be obtained correctly. Please retry"
                showError(error)
            }
        })
    }

    private fun showError(text: String) {
        val adapter = this@EventsFragment.requireActivity()
        val toast = Toast.makeText(adapter, text, Toast.LENGTH_SHORT)
        toast.view.setBackgroundColor(ContextCompat.getColor(adapter, R.color.black))
        toast.view.setBackgroundResource(R.drawable.dw_rounded_toast)
        val text = toast.view.findViewById<View>(android.R.id.message) as TextView
        text.setTextColor(ContextCompat.getColor(adapter, R.color.white))
        text.textSize = 14f
        toast.show()
    }
}