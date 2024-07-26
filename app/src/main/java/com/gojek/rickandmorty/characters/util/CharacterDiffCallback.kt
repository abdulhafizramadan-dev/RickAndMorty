package com.gojek.rickandmorty.characters.util

import androidx.recyclerview.widget.DiffUtil
import com.gojek.rickandmorty.characters.domain.model.Character

object CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name
    }
}