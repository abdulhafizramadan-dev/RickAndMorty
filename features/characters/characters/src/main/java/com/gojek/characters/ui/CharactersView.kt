package com.gojek.characters.ui

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gojek.characters.adapter.CharactersViewAdapter
import com.gojek.characters.shared.domain.model.Character

class CharactersView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        super.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
        super.setAdapter(CharactersViewAdapter())
    }

    private val castedAdapter: CharactersViewAdapter by lazy { adapter as CharactersViewAdapter }

    var eventListener: EventListener?
        get() = castedAdapter.eventListener
        set(value) {
            castedAdapter.eventListener = value
        }

    fun render(message: List<Character>) {
        castedAdapter.submitList(message)
    }

    override fun addItemDecoration(decor: ItemDecoration) {
        throw UnsupportedOperationException("Custom ItemDecoration is not supported for this view")
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        throw UnsupportedOperationException("Custom LayoutManager is not supported for this view.")
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        throw UnsupportedOperationException("Custom adapter is not supported for this view.")
    }

    interface EventListener {
        fun onItemClicked(index: Int, itemId: Int)
    }
}


