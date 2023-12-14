package com.hans.chuckjokes.button

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.hans.chuckjokes.R

class ButtonRandom (context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {

    init {
        // Initialize the background to the first PNG image
        setBackgroundResource(R.drawable.bg_button_default_random)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Handle the button press event
                setBackgroundResource(R.drawable.bg_button_hovered_random) // Set to the second PNG image
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Handle the button release or cancellation event
                setBackgroundResource(R.drawable.bg_button_default_random) // Set to the first PNG image
            }
        }
        return super.onTouchEvent(event)
    }
}