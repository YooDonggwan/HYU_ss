package com.example.takegoal

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView

class RoundImageView : ImageView {
    private val roundRect=RectF()
    private var rectRadius=15f
    private val maskPaint=Paint()
    private val zonePaint=Paint()
    constructor(context: Context?):super(context){
        initView()
    }
    constructor(context: Context?,attrs:AttributeSet?):super(context,attrs){
        initView()
    }
    constructor(context: Context?,attrs:AttributeSet?,defStyleAttr:Int):super(context,attrs,defStyleAttr){
        initView()
    }
    private fun initView(){
        maskPaint.isAntiAlias=true
        maskPaint.xfermode=PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        zonePaint.isAntiAlias=true
        zonePaint.color= Color.WHITE
        rectRadius*=resources.displayMetrics.density
    }
    fun setRectRadius(radius:Float){
        rectRadius=radius
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        roundRect.set(0f,0f,width.toFloat()-rectRadius,height.toFloat()-rectRadius)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.saveLayer(roundRect,zonePaint,Canvas.ALL_SAVE_FLAG)
        canvas?.drawRoundRect(roundRect,rectRadius,rectRadius,zonePaint)
        canvas?.saveLayer(roundRect,maskPaint,Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas?.restore()

    }

}