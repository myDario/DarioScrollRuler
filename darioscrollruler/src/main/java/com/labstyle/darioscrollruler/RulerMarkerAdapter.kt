package com.labstyle.darioscrollruler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class RulerMarkerAdapter(
    private var minValue: Float,
    private var maxValue: Float
): RecyclerView.Adapter<RulerMarkerAdapter.ViewHolder>() {
    companion object {
        const val scrollOverFlowItemsCount = 2
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    private val markerResolution = 5 // marker ticks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            RulerMarkerType.SMALL.ordinal -> R.layout.ruler_marker_small
            RulerMarkerType.MEDIUM.ordinal -> R.layout.ruler_marker_medium
            RulerMarkerType.LARGE.ordinal -> R.layout.ruler_marker_large
            else -> R.layout.ruler_marker_overflow
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        val interval = abs(maxValue - minValue)
        val markersCount = (interval * markerResolution) + 1 + scrollOverFlowItemsCount
        return markersCount.toInt()
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == itemCount - 1)
            return RulerMarkerType.OVERFLOW.ordinal

        val positionWithoutOverflow = position - (scrollOverFlowItemsCount / 2)

        if (positionWithoutOverflow % markerResolution == 0)
            return RulerMarkerType.LARGE.ordinal
        if (positionWithoutOverflow/markerResolution % 2 == 0) {
            return if (positionWithoutOverflow % 2 == 0) RulerMarkerType.MEDIUM.ordinal else RulerMarkerType.SMALL.ordinal
        }
        return if (positionWithoutOverflow % 2 == 0) RulerMarkerType.SMALL.ordinal else RulerMarkerType.MEDIUM.ordinal
    }

    private fun getPositionToValueFactor() =
        (itemCount.toFloat() - 1 - scrollOverFlowItemsCount) / abs(maxValue - minValue)

    fun getValueAtPosition(position: Int): Float {
        val positionWithoutOverflow = position - (scrollOverFlowItemsCount / 2)
        return (positionWithoutOverflow / getPositionToValueFactor()) + minValue
    }

    fun getPositionForValue(value: Float): Int {
        val position = (value - minValue) * getPositionToValueFactor()
        return (position + (scrollOverFlowItemsCount / 2)).toInt()
    }
}