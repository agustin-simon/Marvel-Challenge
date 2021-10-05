package com.intermedia.challenge.ui.characters

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.databinding.FragmentCharactersBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by sharedViewModel()
    private val adapter = CharactersAdapter()
    private var recylerViewState: Parcelable? = null
    private var scrollPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.e("Agus", "activity" + scrollPosition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharactersList()
        setupPagination()
        Log.e("Agus", "viewcreated")
    }

    override fun onStop() {
        scrollPosition = binding.listCharacters.computeVerticalScrollOffset()
        Log.e("Agus", "stop"+  binding.listCharacters.computeVerticalScrollOffset())
        super.onStop()
    }

    private fun setupPagination() {
        binding.listCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreCharacters()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun setupCharactersList() {
        adapter.onClickListener = { character ->
            createIntentData(character)
        }
        binding.listCharacters.adapter = adapter
        viewModel.characters.observe(viewLifecycleOwner, { characters ->
            adapter.addMissingItems(characters)
        })
    }

    private fun createIntentData(character: Character) {
        //Enviamos el objeto al activity 'CharactersActivity'
        //mediante el intent y creamos la nueva activity.
        val intent = Intent(activity, CharactersActivity::class.java)
        intent.putExtra("character_data", character)
        startActivity(intent)
    }
}