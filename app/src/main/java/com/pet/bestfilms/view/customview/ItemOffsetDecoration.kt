package com.pet.bestfilms.view.customview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(val offset: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        if (parent.getChildAdapterPosition(view) == 0){
            outRect.apply {
                right = offset
                bottom = offset
                left = offset
                top = offset
            }
        }
    }
}