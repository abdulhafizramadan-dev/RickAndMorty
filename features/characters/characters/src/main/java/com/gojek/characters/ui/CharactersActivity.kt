package com.gojek.characters.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.gojek.base.presentation.MviEffect
import com.gojek.base.utils.getView
import com.gojek.base.utils.showContent
import com.gojek.base.utils.showError
import com.gojek.base.utils.showLoading
import com.gojek.characters.R
import com.gojek.characters.databinding.ActivityCharactersBinding
import com.gojek.characters.di.CharactersComponentProvider
import com.gojek.characters.presentation.CharactersEffect
import com.gojek.characters.presentation.CharactersIntent
import com.gojek.characters.presentation.CharactersViewModel
import com.gojek.characters.presentation.CharactersViewState
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding

    @Inject
    @Named("charactersViewModelFactory")
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharactersViewModel by viewModels { viewModelFactory }

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CharactersComponentProvider).provideCharactersComponent().inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCharactersBinding.inflate(layoutInflater)
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
        with(binding) {
            if (state.isLoading) {
                msvContainer.showLoading()
                return
            }
            msvContainer.showContent()
            charactersView.render(state.characters)
        }
    }

    private fun produce(effect: MviEffect) {
        when (effect) {
            is CharactersEffect.NavigateEffect -> startActivity(effect.intent)

            is CharactersEffect.ShowErrorNotificationEffect -> {
                with(binding) {
                    msvContainer.showError()
                    val tvErrorMessage = msvContainer.getView<MaterialTextView>(com.gojek.base.R.id.tv_error_message)
                    tvErrorMessage?.text = effect.cause.message
                    val btnTryAgain = msvContainer.getView<MaterialButton>(com.gojek.base.R.id.btn_try_again)
                    btnTryAgain?.setOnClickListener {
                        msvContainer.showLoading()
                        refresh()
                    }
                }
            }
        }
    }

    private fun refresh() {
        viewModel.processIntents(
            Observable.just(CharactersIntent.SeeAllCharactersIntent)
        )
    }
}