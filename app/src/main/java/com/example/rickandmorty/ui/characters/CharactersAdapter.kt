package com.example.rickandmorty.ui.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.databinding.ItemCharacterBinding

class CharactersAdapter(private val listener: CharacterItemListener) :
    PagingDataAdapter<Character, CharacterViewHolder>(DIFF_CALLBACK) {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
         getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character) =
                oldItem == newItem

        }
    }
}

class CharacterViewHolder(private val itemBinding: ItemCharacterBinding, private val listener: CharactersAdapter.CharacterItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var character: Character

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Character) {
        this.character = item
        itemBinding.name.text = item.name
        itemBinding.speciesAndStatus.text = """${item.species} - ${item.status}"""
        Glide.with(itemBinding.root)
            .load(item.image)
            .transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(character.id)
    }
}

