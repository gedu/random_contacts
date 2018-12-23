package com.teddy.sweatcontacts.common.widget

import android.content.Context
import android.util.AttributeSet
import com.airbnb.lottie.LottieAnimationView

private const val FORWARD = 1f
private const val REVERSE = -1f
private const val END = 1f
private const val START = 0f

class ClickableLottieView : LottieAnimationView {

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        speed = REVERSE
    }

    fun startFromEnd() {
        progress = END
        speed = FORWARD
    }

    fun startFromStart() {
        progress = START
        speed = REVERSE
    }

    fun playSwap() {
        if (speed == FORWARD) playReverse() else playForward()
    }

    fun playForward() {
        speed = FORWARD
        playAnimation()
    }

    fun playReverse() {
        speed = REVERSE
        playAnimation()
    }
}