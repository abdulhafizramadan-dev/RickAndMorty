package com.gojek.characters.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.gojek.characters.databinding.CharacterViewBinding
import com.gojek.characters.shared.domain.model.Character

class CharacterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CharacterViewBinding by lazy {
        CharacterViewBinding.inflate(LayoutInflater.from(context), this)
    }

    init {
        setPadding(8)
    }

    fun render(character: Character) {
        Glide.with(binding.avatarView)
            .load(character.image)
            .into(binding.avatarView)
        binding.tvName.text = character.name
        binding.tvStatus.text = character.status
    }
}