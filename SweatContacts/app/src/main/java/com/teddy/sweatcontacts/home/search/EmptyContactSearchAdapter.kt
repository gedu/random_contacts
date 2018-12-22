package com.teddy.sweatcontacts.home.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.teddy.sweatcontacts.R
import com.teddy.sweatcontacts.common.extensions.inflate

class EmptyContactSearchAdapter: RecyclerView.Adapter<EmptyContactSearchAdapter.EmptySearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptySearchViewHolder {
        val view = parent.inflate(R.layout.contact_search_empty_item_list)
        return EmptySearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmptySearchViewHolder, position: Int) { }

    override fun getItemCount() = 1

    inner class EmptySearchViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView)
}