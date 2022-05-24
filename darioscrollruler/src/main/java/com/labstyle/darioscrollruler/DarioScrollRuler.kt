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
    private lateinit var rulerAdapter: RulerMarkerAdapter
    private var rulerMarkers: RecyclerView
    private var snapHelper: LinearSnapHelper
    private var currentPositionValue: Float = 0f

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
        rulerMarkers.addItemDecoration(RulerMarkerDecoration())
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rulerMarkers)

        reload(minValue, maxValue, initialValue)
    }

    fun scrollToValue(value: Float, smoothScroll: Boolean = false) {
        val position = rulerAdapter.getPositionForValue(value)
        val dx = (position) * RulerMarkerDecoration.getItemWidthPx()
        if (smoothScroll) {
            rulerMarkers.post {
                rulerMarkers.smoothScrollBy(dx, 0)
            }
            return
        }

        if (currentPositionValue == value) {
            return
        }

        rulerMarkers.post{
            rulerMarkers.scrollBy(dx, 0)
            scrollToValue(value)
        }
    }

    fun reload(min: Float, max: Float, initValue: Float, smoothScroll: Boolean = false) {
        minValue = min
        maxValue = max
        initialValue = initValue

        rulerAdapter = RulerMarkerAdapter(minValue, maxValue)
        rulerMarkers.adapter = rulerAdapter

        val onScrollListener = RulerSnapOnScrollListener(
            snapHelper,
            RulerSnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL
        ) { position ->
            currentPositionValue = rulerAdapter.getValueAtPosition(position)
            scrollListener?.onRulerScrolled(currentPositionValue)
        }
        rulerMarkers.addOnScrollListener(onScrollListener)

        rulerMarkers.post {
            rulerMarkers.scrollBy(5, 0)
        }
        scrollToValue(initialValue, smoothScroll)
    }
}