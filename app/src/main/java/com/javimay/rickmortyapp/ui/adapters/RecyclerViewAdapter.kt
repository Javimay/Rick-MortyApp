package com.javimay.rickmortyapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.javimay.rickmortyapp.R
import com.javimay.rickmortyapp.data.db.entities.Character

class RecyclerViewAdapter(
    private val characterList: MutableList<Character>,
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((position:Int) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initBindHolder(characterList[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun initBindHolder(character: Character, position: Int) {
            val ivCharacter = itemView.findViewById<ImageView>(R.id.ivCharacter)
            ivCharacter.setImageBitmap(character.image)
            val tvCharacterName = itemView.findViewById<TextView>(R.id.tvCharacterName)
            tvCharacterName.text = character.name

            itemView.setOnClickListener {
                onItemClick?.invoke(position)
            }
        }
    }
}