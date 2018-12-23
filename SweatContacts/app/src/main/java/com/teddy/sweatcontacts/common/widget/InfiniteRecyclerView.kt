package com.teddy.sweatcontacts.common.widget

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

const val VIEW_LOADING_TYPE = 0
const val VIEW_CONTENT_TYPE = 1

class InfiniteRecyclerView : RecyclerView {

    private val isLoadingMore = MutableLiveData<Boolean>()
    val reachBottom get() = isLoadingMore as LiveData<Boolean>

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        addOnScrollListener(bottomScrollListener)
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        (layout as GridLayoutManager).spanSizeLookup = spanLookup
        super.setLayoutManager(layout)
    }

    fun loadingFinished() {
        isLoadingMore.value = false
    }

    private val bottomScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                val totalItems = layoutManager?.itemCount ?: 0
                var lastVisibleItem = 0

                if (layoutManager != null) {
                    lastVisibleItem = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                }

                //note: +3 how many of mItems to have below the current scroll position before loading more
                val isLoading = isLoadingMore.value ?: false
                if (!isLoading && totalItems <= (lastVisibleItem + 3)) {
                    isLoadingMore.value = true
                }
            }
        }
    }

    private val spanLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val isLoading = isLoadingMore.value ?: false
            return if (isLoading && adapter?.getItemViewType(position) == VIEW_LOADING_TYPE)
                (layoutManager as GridLayoutManager).spanCount
            else
                1
        }
    }
}