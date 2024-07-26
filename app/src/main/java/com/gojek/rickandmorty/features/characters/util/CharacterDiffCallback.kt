package com.gojek.rickandmorty.features.characters.util

import androidx.recyclerview.widget.DiffUtil
import com.gojek.rickandmorty.features.characters.domain.model.Character

object CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name
    }
}