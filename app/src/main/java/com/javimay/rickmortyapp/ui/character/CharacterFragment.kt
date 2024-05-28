package com.javimay.rickmortyapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var navController: NavController
    private lateinit var charactersList: MutableList<Character>
    private val characterViewModel: CharacterFragmentViewModel by viewModels()
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        navController = findNavController()
        val characterId = args.id
        downloadCharacters(characterId)
        return binding.root
    }

    private fun downloadCharacters(characterId: Long) {
        characterViewModel.getCharacter(characterId).observe(viewLifecycleOwner) {
            if (it != null) {

            }
        }
    }
}