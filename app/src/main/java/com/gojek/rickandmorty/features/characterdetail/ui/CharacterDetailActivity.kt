package com.gojek.rickandmorty.features.characterdetail.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.gojek.rickandmorty.R
import com.gojek.rickandmorty.base.presentation.MviEffect
import com.gojek.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.DefaultGetCharacterDetailUseCase
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailActionProcessor
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailEffect
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailIntent
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailViewModel
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailViewState
import com.gojek.rickandmorty.features.characters.data.CharacterMapper
import com.gojek.rickandmorty.features.characters.data.CharacterRepositoryImpl
import com.gojek.rickandmorty.utils.ActionConstant
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CharacterDetailActivity : AppCompatActivity() {

    private val viewModelFactory: ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val chucker = OkHttpClient.Builder()
                    .addInterceptor(
                        ChuckerInterceptor.Builder(this@CharacterDetailActivity).build()
                    )
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .client(chucker)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return CharacterDetailViewModel(
                    actionProcessor = CharacterDetailActionProcessor(
                        characterDetailUseCase = DefaultGetCharacterDetailUseCase(
                            characterRepository = CharacterRepositoryImpl(
                                api = retrofit.create(),
                                characterMapper = CharacterMapper()
                            )
                        )
                    )
                ) as T
            }
        }

    private lateinit var binding: ActivityCharacterDetailBinding
    private val viewModel: CharacterDetailViewModel by viewModels { viewModelFactory }

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
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