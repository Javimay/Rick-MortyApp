package com.javimay.rickmortyapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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
    private lateinit var charactersList: MutableSet<Character>
    private lateinit var adapter: RecyclerViewAdapter
    private var download: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()
        if (::charactersList.isInitialized.not()) {
            downloadCharacters()
        } else {
            initRecyclerView()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initSearchBar()
    }

    private fun showLoading(show: Boolean) {
        download = show
        binding.pbLoader.isVisible = show
        binding.rvCharacters.isVisible = !show
        binding.etSearchBar.isVisible = !show
    }

    private fun downloadCharacters() {
        showLoading(true)
        homeViewModel.getCharacter().observe(viewLifecycleOwner) {
            if (it != null) {
                charactersList = it.toMutableSet()
                initRecyclerView()
            }
        }
    }

    private fun downloadCharactersByPage() {
        homeViewModel.getCharactersByPage().observe(viewLifecycleOwner) {
            if (it != null) {
                charactersList.addAll(it.toMutableList())
                adapter.updateCharactersList(it.toMutableList())
                binding.srlRefresh.isRefreshing = false
            }
        }
    }

    private fun initSearchBar() {
        binding.etSearchBar.apply {
            text.clear()
            addTextChangedListener {
                adapter.getFilter().filter(it.toString())
            }

        }
        binding.ibClearSearch.setOnClickListener {
            binding.etSearchBar.text.clear()
        }
    }

    private fun initRecyclerView() {
        val viewManager = GridLayoutManager(context, 2)
        adapter = RecyclerViewAdapter(charactersList.toMutableList())
        adapter.onItemClick = { character ->
            goToContentCharacterFragment(character)
        }
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager = viewManager
        showLoading(false)
        initSwipeRefreshLayout()
    }

    private fun initSwipeRefreshLayout() {
        binding.srlRefresh.apply {
            setOnRefreshListener {
                if (this.isRefreshing)
                    downloadCharactersByPage()
            }
        }
    }

    private fun goToContentCharacterFragment(character: Character) {
        val action = HomeFragmentDirections.actionHomeFragmentToCharacterFragment(
            character.toCharacterDto()
        )
        navController.navigate(action)
    }
}