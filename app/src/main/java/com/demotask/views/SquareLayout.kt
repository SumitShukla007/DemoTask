package com.demotask.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout


class SquareLayout : LinearLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val mScale = 1
        if (width < (mScale * height + 0.5).toInt()) {
            width = (mScale * height + 0.5).toInt()
        } else {
            height = (width / mScale + 0.5).toInt()
        }
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }
}