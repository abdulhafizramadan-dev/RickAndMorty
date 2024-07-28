package com.gojek.rickandmorty.features.characterdetail.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.gojek.rickandmorty.R
import com.gojek.rickandmorty.RickAndMortyApplication
import com.gojek.rickandmorty.base.presentation.MviEffect
import com.gojek.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailEffect
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailIntent
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailViewModel
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailViewState
import com.gojek.rickandmorty.utils.ActionConstant
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    @Inject
    lateinit var viewModel: CharacterDetailViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RickAndMortyApplication).rickAndMortyComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra(ActionConstant.CHARACTER_ID, 0)

        viewModel.processIntents(
            Observable.just(CharacterDetailIntent.LoadCharacterDetailIntent(characterId = id)),
            binding.toolbar.clicks().map { CharacterDetailIntent.BackPressedIntent }
        )

        disposable.addAll(
            viewModel.states().subscribe(::render),
            viewModel.effects().subscribe(::produce)
        )
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    private fun render(state: CharacterDetailViewState) {
        with(binding) {
            Glide.with(this@CharacterDetailActivity)
                .load(state.character.image)
                .into(ivImage)
            tvName.text = state.character.name
            tvSpecies.text = state.character.species
            tvGender.text = state.character.gender
        }
    }

    private fun produce(effect: MviEffect) {
        when (effect) {
            is CharacterDetailEffect.BackPressedEffect ->
                finish()

            is CharacterDetailEffect.ShowErrorNotificationEffect ->
                Log.d("TAG", "produce: Error = ${effect.cause.message}")
        }
    }
}