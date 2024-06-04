package com.javimay.rickmortyapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.data.db.entities.toCharacterDto
import com.javimay.rickmortyapp.databinding.FragmentHomeBinding
import com.javimay.rickmortyapp.ui.adapters.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private val homeViewModel: HomeFragmentViewModel by viewModels()
    private lateinit var charactersList: MutableList<Character>
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()
        showLoading(true)
        downloadCharacters()
        return binding.root
    }

    private fun showLoading(show: Boolean) {
        binding.pbLoader.isVisible = show
        binding.rvCharacters.isVisible = !show
        binding.svSearchBar.isVisible = !show
    }

    private fun downloadCharacters() {
        homeViewModel.getCharacter().observe(viewLifecycleOwner) {
            if (it != null) {
                charactersList = it.toMutableList()
                initRecyclerView()
            }
        }
    }

    private fun initSearchBar() {
        binding.svSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return true
            }
        })
    }

    private fun initRecyclerView() {
        val viewManager = GridLayoutManager(context, 2)
        adapter = RecyclerViewAdapter(charactersList)
        adapter.onItemClick = { position ->
            goToContentCharacterFragment(charactersList[position])
        }
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager = viewManager
        showLoading(false)
        initSearchBar()
    }

    private fun goToContentCharacterFragment(character: Character) {
        val action = HomeFragmentDirections.actionHomeFragmentToCharacterFragment(
            character.toCharacterDto()
        )
        navController.navigate(action)
    }
}