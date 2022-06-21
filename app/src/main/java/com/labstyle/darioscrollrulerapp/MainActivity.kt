package com.labstyle.darioscrollrulerapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import com.labstyle.darioscrollruler.DarioScrollRuler
import com.labstyle.darioscrollruler.ScrollRulerListener

class MainActivity : AppCompatActivity() {
    private lateinit var ruller: DarioScrollRuler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ruller = findViewById(R.id.darioScrollRuler)
        ruller.scrollListener = object: ScrollRulerListener {
            override fun onRulerScrolled(value: Float) {
                findViewById<TextView>(R.id.textviewValue).text = "$value"
            }
        }

        ruller.postDelayed({
            val minHeightCm = 50f
            val maxHeightCm = 250f
            val initialValueCm = feetToCm(6.01f)

            Log.d("rafff", "min ${cmToFeet(minHeightCm) * 10} max ${cmToFeet(maxHeightCm) * 10} initValue ${cmToFeet(initialValueCm) * 10}")
            ruller.reload(
                min = cmToFeet(minHeightCm) * 10,
                max = cmToFeet(maxHeightCm) * 10,
                initValue = cmToFeet(initialValueCm) * 10)
        }, 5000)
    }

    fun cmToFeet(cm: Float): Float = cm * 0.0328084f
    fun feetToCm(feet: Float): Float = feet / 0.0328084f
}