package com.gojek.character.detail.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gojek.base.presentation.MviEffect
import com.gojek.base.utils.ActionConstant
import com.gojek.base.utils.getView
import com.gojek.base.utils.showContent
import com.gojek.base.utils.showError
import com.gojek.base.utils.showLoading
import com.gojek.character.detail.R
import com.gojek.character.detail.databinding.ActivityCharacterDetailBinding
import com.gojek.character.detail.di.CharacterDetailComponentProvider
import com.gojek.character.detail.presentation.CharacterDetailEffect
import com.gojek.character.detail.presentation.CharacterDetailIntent
import com.gojek.character.detail.presentation.CharacterDetailViewModel
import com.gojek.character.detail.presentation.CharacterDetailViewState
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    @Inject
    @Named("characterDetailViewModelFactory")
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharacterDetailViewModel by viewModels { viewModelFactory }

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as CharacterDetailComponentProvider).provideCharacterDetailComponent().inject(this)
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
            if (state.isLoading) {
                msvContainer.showLoading()
                return
            }
            msvContainer.showContent()
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

            is CharacterDetailEffect.ShowErrorNotificationEffect -> {
                with(binding) {
                    msvContainer.showError()
                    val tvErrorMessage =
                        msvContainer.getView<MaterialTextView>(com.gojek.base.R.id.tv_error_message)
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
            Observable.just(
                CharacterDetailIntent.LoadCharacterDetailIntent(characterId = intent.getIntExtra(
                ActionConstant.CHARACTER_ID, 0)))
        )
    }
}