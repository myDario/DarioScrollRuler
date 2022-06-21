package com.labstyle.darioscrollruler

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition
import kotlin.math.abs

class DarioScrollRuler @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
): RelativeLayout(context, attrs, defStyle, defStyleRes) {
    private lateinit var rulerAdapter: RulerMarkerAdapter
    private var rulerMarkers: RecyclerView
    private var snapHelper: LinearSnapHelper
    var currentPositionValue: Float = 0f
        private set

    var scrollListener: ScrollRulerListener? = null
    var minValue = 0f
    var maxValue = 100f
    var initialValue = (minValue + maxValue) / 2
    private var onScrollListener: RulerSnapOnScrollListener

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

        onScrollListener = RulerSnapOnScrollListener(
            snapHelper,
            RulerSnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL
        ) { snapPosition ->
            broadcastValue(snapPosition)
        }
        rulerMarkers.addOnScrollListener(onScrollListener)

        reload(minValue, maxValue, initialValue)
    }

    private fun broadcastValue(position: Int) {
        currentPositionValue = rulerAdapter.getValueAtPosition(position)
        scrollListener?.onRulerScrolled(currentPositionValue)
    }

    fun scrollToValue(value: Float) {
        val position = rulerAdapter.getPositionForValue(value)

        rulerMarkers.scrollToPosition(position)

        // this is to fix the scroll to position with snapHelper
        rulerMarkers.post {
            val layoutManager = rulerMarkers.layoutManager as LinearLayoutManager
            val view = layoutManager.findViewByPosition(position)
            if (view != null) {
                snapHelper.calculateDistanceToFinalSnap(layoutManager, view)?.let { snapDistance ->
                    if (snapDistance[0] != 0  || snapDistance[1] != 0) {
                        rulerMarkers.scrollBy(snapDistance[0], snapDistance[1])
                    }
                }
            }
        }

        broadcastValue(position)
    }

    fun reload(min: Float, max: Float, initValue: Float) {
        minValue = min
        maxValue = max
        initialValue = initValue

        rulerAdapter = RulerMarkerAdapter(minValue, maxValue)
        rulerMarkers.adapter = rulerAdapter

        rulerMarkers.post {
            rulerMarkers.scrollBy(5, 0)
        }
        scrollToValue(initialValue)
    }
}