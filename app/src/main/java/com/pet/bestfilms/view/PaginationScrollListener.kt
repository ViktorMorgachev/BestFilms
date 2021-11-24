package com.pet.bestfilms.view

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: RecyclerView.LayoutManager): RecyclerView.OnScrollListener() {

   override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
       val  recyclerViewHelper = RecyclerViewHelper(recyclerView, layoutManager)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition: Int = recyclerViewHelper.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }
    protected abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}



class RecyclerViewHelper(private val recyclerView: RecyclerView, private val  layoutManager: RecyclerView.LayoutManager){


    fun findFirstVisibleItemPosition(): Int {
        val child = findOneVisibleChild(0, layoutManager.childCount,
            completelyVisible = false,
            acceptPartiallyVisible = true)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(child)
    }

    fun getItemCount(): Int {
        return layoutManager.itemCount ?: 0
    }

    fun findFirstCompletelyVisibleItemPosition(): Int {
        val child = findOneVisibleChild(0, layoutManager.childCount,
            completelyVisible = true,
            acceptPartiallyVisible = false)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child)
    }

    fun findLastVisibleItemPosition(): Int {
        val child = findOneVisibleChild(layoutManager.childCount - 1, -1,
            completelyVisible = false,
            acceptPartiallyVisible = true)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child)
    }
    fun findLastCompletelyVisibleItemPosition(): Int {
        val child = findOneVisibleChild(layoutManager.childCount - 1, -1,
            completelyVisible = true,
            acceptPartiallyVisible = false)
        return if (child == null) RecyclerView.NO_POSITION else recyclerView.getChildAdapterPosition(
            child)
    }

    private fun findOneVisibleChild(
        fromIndex: Int, toIndex: Int, completelyVisible: Boolean,
        acceptPartiallyVisible: Boolean,
    ): View? {
        val helper: OrientationHelper = if (layoutManager.canScrollVertically()) {
            OrientationHelper.createVerticalHelper(layoutManager)
        } else {
            OrientationHelper.createHorizontalHelper(layoutManager)
        }
        val start = helper.startAfterPadding
        val end = helper.endAfterPadding
        val next = if (toIndex > fromIndex) 1 else -1
        var partiallyVisible: View? = null
        var i = fromIndex
        while (i != toIndex) {
            val child = layoutManager.getChildAt(i)
            val childStart = helper.getDecoratedStart(child)
            val childEnd = helper.getDecoratedEnd(child)
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child
                    }
                } else {
                    return child
                }
            }
            i += next
        }
        return partiallyVisible
    }
}
