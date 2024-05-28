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
import com.javimay.rickmortyapp.data.model.CharacterDto
import com.javimay.rickmortyapp.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var navController: NavController
    private lateinit var character: MutableList<CharacterDto>
    private val characterViewModel: CharacterFragmentViewModel by viewModels()
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        navController = findNavController()
        val character = args.character
        bindCharacterData(character)
        return binding.root
    }

    private fun bindCharacterData(character: CharacterDto) {
        binding.ivImageCharacter.setImageBitmap(character.image)
        binding.tvNameCharacter.text = character.name
        binding.tvGenderValue.text = character.gender
        binding.tvSpecieValue.text = character.species
        binding.tvStatusValue.text = character.status
        binding.tvOriginValue.text = character.originId.toString()
        binding.tvLocationValue.text = character.locationId.toString()
    }
}