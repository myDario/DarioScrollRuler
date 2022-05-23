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

            ruller.reload(min = 50f, max = 250f, initValue = 180f)
        }
    }
}