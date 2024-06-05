package com.javimay.rickmortyapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.javimay.rickmortyapp.R
import com.javimay.rickmortyapp.data.db.entities.Character
import java.util.Locale

class RecyclerViewAdapter(
    private val characterList: MutableList<Character>,
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((character: Character) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    private var initialCharacterList = mutableListOf<Character>().apply {
        addAll(characterList)
    }

    private val characterFilter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filteredCharacters = mutableListOf<Character>()
            if (charSequence.isNullOrEmpty()) {
                initialCharacterList.let { filteredCharacters.addAll(it) }
            } else {
                val query = charSequence.toString().trim().lowercase(Locale.getDefault())
                initialCharacterList.forEach {
                    if (it.name.lowercase(Locale.getDefault()).contains(query)) {
                        filteredCharacters.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredCharacters
            return results
        }

        override fun publishResults(charSequence: CharSequence?, results: FilterResults?) {
            results?.let {
                if (results.values is MutableList<*>) {
                    val mutableCharacters = results.values as MutableList<Character>
                    characterList.clear()
                    characterList.addAll(mutableCharacters.toList())
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initBindHolder(characterList[position], position)
    }

    fun getFilter(): Filter {
        return characterFilter
    }

    fun updateCharactersList(characters: List<Character>) {
        var characterPosition = characterList.size
        characters.forEach { character ->
            characterPosition++
            characterList.add(character)
            notifyItemInserted(characterPosition)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun initBindHolder(character: Character, position: Int) {
            val ivCharacter = itemView.findViewById<ImageView>(R.id.ivCharacter)
            ivCharacter.setImageBitmap(character.image)
            val tvCharacterName = itemView.findViewById<TextView>(R.id.tvCharacterName)
            tvCharacterName.text = character.name

            itemView.setOnClickListener {
                onItemClick?.invoke(characterList[position])
            }
        }
    }
}