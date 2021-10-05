package com.intermedia.challenge.ui.characters

import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.data.models.NetResult
import com.intermedia.challenge.data.repositories.CharactersRepository
import com.intermedia.challenge.ui.events.EventsFragment
import kotlinx.coroutines.launch

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters
    private val charactersList = mutableListOf<Character>()
    private var amount: Int = 0

    init {
        loadCharacters(0)
    }

    private fun loadCharacters(offset: Int) {
        viewModelScope.launch {
            when (val response = charactersRepository.getCharacters(offset)) {
                is NetResult.Success -> {
                    charactersList.addAll(response.data.charactersList.characters)
                    _characters.postValue(charactersList)
                }
                is NetResult.Error -> {
                    val error = "The data could not be obtained correctly. Please retry"
                    Log.e(response.toString(), "Error Get Data")
                    showError(error)
                }
            }
        }
    }

    fun loadMoreCharacters() {
        val limit = 15
        amount+= limit
        loadCharacters(amount)
    }

    private fun showError(text: String) {
        val adapter = EventsFragment()
        val toast = Toast.makeText( adapter?.context,text, Toast.LENGTH_SHORT)
        toast.view.setBackgroundColor(ContextCompat.getColor(adapter.requireContext(), R.color.black))
        toast.view.setBackgroundResource(R.drawable.dw_rounded_toast)
        val text = toast.view.findViewById<View>(android.R.id.message) as TextView
        text.setTextColor(ContextCompat.getColor(adapter.requireContext(), R.color.white))
        text.textSize = 14f
        toast.show()
    }
}