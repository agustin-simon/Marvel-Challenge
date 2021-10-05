package com.intermedia.challenge.ui.characters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.intermedia.challenge.databinding.ActivityCharactersBinding
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.ui.comics.ComicsAdapter

class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding
    private lateinit var character: Character
    private val adapter = ComicsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        character = intent.getParcelableExtra("character_data")
        binding.character = character
        binding.listComicsCharacter.adapter = adapter
        adapter.update(character.comics.appearances)
        binding.listComicsCharacter.layoutManager = LinearLayoutManager(this)

        binding.iconCloseDetails.setOnClickListener {
            finish()
        }
    }
}