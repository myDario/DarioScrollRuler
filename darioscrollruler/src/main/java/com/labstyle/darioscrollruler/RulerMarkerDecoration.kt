package com.labstyle.darioscrollruler

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RulerMarkerDecoration: RecyclerView.ItemDecoration() {
    companion object {
        const val markerWidthDps = 1
        const val spaceDps = 2

        fun dpsToPx(n: Number) = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            n.toFloat(),
            Resources.getSystem().displayMetrics)

        fun getItemWidthPx(): Int =
            dpsToPx(markerWidthDps + (spaceDps * 2)).toInt()

        fun getFirsItemWidthPx(parent: RecyclerView): Int =
            ((parent.width / 2) + dpsToPx(markerWidthDps + spaceDps)).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacePx = dpsToPx(spaceDps).toInt()

        // first item
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = (parent.width / 2) + spacePx
            outRect.right = spacePx
            return
        }

        // last item
        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.left = spacePx
            outRect.right = (parent.width / 2) + spacePx
            return
        }

        outRect.left = spacePx
        outRect.right = spacePx
    }
}
