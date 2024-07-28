package com.gojek.characters.adapter

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gojek.characters.shared.domain.model.Character
import com.gojek.characters.ui.CharacterView
import com.gojek.characters.ui.CharactersView
import com.gojek.characters.util.CharacterDiffCallback

class CharactersViewAdapter :
    ListAdapter<Character, CharactersViewAdapter.ViewHolder>(
        CharacterDiffCallback
    ),
    View.OnClickListener {

    inner class ViewHolder(val view: CharacterView) : RecyclerView.ViewHolder(view)

    init {
        setHasStableIds(true)
    }

    var eventListener: CharactersView.EventListener? = null

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = CharacterView(parent.context)
        view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        view.setOnClickListener(this)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.view.render(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    override fun onClick(v: View?) {
        if (v == null) return
        val listener = this.eventListener ?: return
        val index = recyclerView?.getChildAdapterPosition(v) ?: return
        listener.onItemClicked(index, currentList[index].id)
    }
}
