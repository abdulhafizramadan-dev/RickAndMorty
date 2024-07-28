package com.gojek.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.gojek.rickandmorty.base.presentation.MviEffect
import com.gojek.rickandmorty.databinding.ActivityMainBinding
import com.gojek.rickandmorty.features.characters.data.CharacterMapper
import com.gojek.rickandmorty.features.characters.data.CharacterRepositoryImpl
import com.gojek.rickandmorty.features.characters.domain.usecase.DefaultGetCharactersUseCase
import com.gojek.rickandmorty.features.characters.presentation.CharactersActionProcessor
import com.gojek.rickandmorty.features.characters.presentation.CharactersEffect
import com.gojek.rickandmorty.features.characters.presentation.CharactersIntent
import com.gojek.rickandmorty.features.characters.presentation.CharactersViewModel
import com.gojek.rickandmorty.features.characters.presentation.CharactersViewState
import com.gojek.rickandmorty.features.characters.ui.CharactersView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: CharactersViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as RickAndMortyApplication).rickAndMortyComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.processIntents(
            Observable.just(CharactersIntent.SeeAllCharactersIntent),
            Observable.create { emitter ->
                binding.charactersView.eventListener = object : CharactersView.EventListener {
                    override fun onItemClicked(index: Int, itemId: Int) {
                        emitter.onNext(CharactersIntent.SeeDetailCharacterIntent(itemId))
                    }
                }
            }
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

    private fun render(state: CharactersViewState) {
        binding.charactersView.render(state.characters)
    }

    private fun produce(effect: MviEffect) {
        when (effect) {
            is CharactersEffect.NavigateEffect -> startActivity(effect.intent)

            is CharactersEffect.ShowErrorNotificationEffect -> {
                Log.d(MainActivity::class.java.name, "produce: Error = ${effect.cause.message}")
            }
        }
    }
}