package com.gojek.base.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class AbstractViewModel<S : MviState>(
    private val actionProcessor: ObservableTransformer<MviAction, MviResult>,
    private val initialState: S
) : ViewModel() {

    abstract fun mapIntentToActions(intent: MviIntent): Observable<MviAction>
    abstract fun reduceState(previous: S, result: MviResult): S
    abstract fun inferSideEffectsFromResult(result: MviResult): List<MviEffect>

    private var upstreamDisposable: Disposable? = null
    private var intentsDisposable: Disposable? = null

    private val _intents = PublishSubject.create<MviIntent>()
    private val _effects = PublishSubject.create<MviEffect>()
    private val _states: Observable<S> =
        _intents.observeOn(Schedulers.io())
            .flatMap { mapIntentToActions(it) }
            .compose(actionProcessor)
            .doOnNext { inferSideEffectsFromResult(it).forEach { effect -> _effects.onNext(effect) } }
            .scan(initialState) { previous: S, result: MviResult -> reduceState(previous, result) }
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0) { disposable -> upstreamDisposable = disposable }

    fun states(): Observable<S> {
        return _states.hide().observeOn(AndroidSchedulers.mainThread())
    }

    fun effects(): Observable<MviEffect> {
        return _effects.hide().observeOn(AndroidSchedulers.mainThread())
    }

    fun processIntents(intent: Observable<MviIntent>) {
        intentsDisposable?.dispose()
        intentsDisposable = intent.subscribe({ _intents.onNext(it) }, { _intents.onError(it) })
    }

    fun processIntents(vararg intents: Observable<MviIntent>) {
        processIntents(Observable.merge(intents.toList()))
    }

    override fun onCleared() {
        upstreamDisposable?.dispose()
        intentsDisposable?.dispose()
        _intents.onComplete()
        _effects.onComplete()
        super.onCleared()
    }
}