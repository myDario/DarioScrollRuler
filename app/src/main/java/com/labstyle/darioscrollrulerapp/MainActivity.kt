package com.labstyle.darioscrollrulerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.labstyle.darioscrollruler.DarioScrollRuler
import com.labstyle.darioscrollruler.ScrollRulerListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<DarioScrollRuler>(R.id.darioScrollRuler)?.let { ruller ->
            ruller.scrollListener = object: ScrollRulerListener {
                override fun onRulerScrolled(value: Float) {
                    findViewById<TextView>(R.id.textviewValue).text = "$value"
                }
            }

            ruller.postDelayed({
                val minHeightCm = 50f
                val maxHeightCm = 250f
                val initialValueCm = feetToCm(6.01f)

                ruller.reload(
                    min = cmToFeet(minHeightCm) * 10,
                    max = cmToFeet(maxHeightCm) * 10,
                    initValue = cmToFeet(initialValueCm) * 10)
            }, 5000)
        }
    }

    fun cmToFeet(cm: Float): Float = cm * 0.0328084f
    fun feetToCm(feet: Float): Float = feet / 0.0328084f
}