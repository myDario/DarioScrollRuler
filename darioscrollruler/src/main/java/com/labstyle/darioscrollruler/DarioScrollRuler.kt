package com.labstyle.darioscrollruler

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class DarioScrollRuler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
): RelativeLayout(context, attrs, defStyle, defStyleRes) {
    private var rulerAdapter: RulerMarkerAdapter
    private var rulerMarkers: RecyclerView

    var scrollListener: ScrollRulerListener? = null
    var minValue = 0f
    var maxValue = 100f
    var initialValue = (minValue + maxValue) / 2

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DarioScrollRuler)
        minValue = attributes.getFloat(R.styleable.DarioScrollRuler_minValue, minValue)
        maxValue = attributes.getFloat(R.styleable.DarioScrollRuler_maxValue, maxValue)
        initialValue = attributes.getFloat(R.styleable.DarioScrollRuler_initialValue, initialValue)
        attributes.recycle()

        inflate(context, R.layout.dario_scroll_ruler, this)

        rulerMarkers = findViewById(R.id.rulerMarkers)
        rulerAdapter = RulerMarkerAdapter(minValue, maxValue)
        rulerMarkers.adapter = rulerAdapter
        rulerMarkers.addItemDecoration(RulerMarkerDecoration())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rulerMarkers)

        val onScrollListener = RulerSnapOnScrollListener(
            snapHelper,
            RulerSnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL
        ) { position ->
            val positionValue = rulerAdapter.getValueAtPosition(position)
            scrollListener?.onRulerScrolled(positionValue)
        }
        rulerMarkers.addOnScrollListener(onScrollListener)

        rulerMarkers.post {
            rulerMarkers.scrollBy(5, 0)
        }
        scrollToValue(initialValue)
    }

    fun scrollToValue(value: Float) {
        val position = rulerAdapter.getPositionForValue(value)
        //Log.d("rafff", "value: $value position for value: $position ")
        val dx = (position) * RulerMarkerDecoration.getItemWidthPx()
        rulerMarkers.post { rulerMarkers
            rulerMarkers.scrollBy(dx, 0)
            //rulerMarkers.smoothScrollBy(dx, 0)

            //rulerMarkers.scrollToPosition(position)
        }
    }
}