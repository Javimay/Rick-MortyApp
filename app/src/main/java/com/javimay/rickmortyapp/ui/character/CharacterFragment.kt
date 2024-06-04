package com.javimay.rickmortyapp.ui.character

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.javimay.rickmortyapp.data.db.entities.toLocationDtoList
import com.javimay.rickmortyapp.data.model.CharacterDto
import com.javimay.rickmortyapp.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private lateinit var navController: NavController
    private val characterViewModel: CharacterFragmentViewModel by viewModels()
    private val args: CharacterFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        navController = findNavController()
        args.let {
            val character = args.character
            bindCharacterData(character)
            getCharacterLocations(character)
        }
        return binding.root
    }

    private fun getCharacterLocations(character: CharacterDto) {
        val locationIds = listOf(character.locationId, character.originId)
        characterViewModel.getCharacterLocations(locationIds)
            .observe(viewLifecycleOwner) { location ->
                if (location.isNotEmpty()) {
                    val locationDto = location.toLocationDtoList()
                    if (locationDto.size == 1){
                        character.origin = locationDto.first()
                        character.location = locationDto.first()
                    } else {
                        character.location = locationDto.find { it.locationId == character.locationId }
                        character.origin = locationDto.find { it.locationId == character.originId }
                    }
                    bindCharacterData(character)
                }
            }
    }

    private fun bindCharacterData(character: CharacterDto) {
        binding.ivImageCharacter.setImageBitmap(character.image)
        binding.tvNameCharacter.text = character.name
        binding.tvGenderValue.text = character.gender
        binding.tvSpecieValue.text = character.species
        binding.tvStatusValue.text = character.status
        binding.tvOriginValue.text = character.origin?.name
        binding.tvLocationValue.text = character.location?.name
    }
}