package com.masterj.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet

/**
 * Created by wyl on 2023/8/15
 * Describe:
 */
internal class EnglishSentenceTextView : androidx.appcompat.widget.AppCompatTextView {

    private var mBackgroundPaint: Paint = Paint()
    private var underlinePaint: Paint = Paint()

    private val maxLineCount = 2 // 最大两行

    private var lineBackgroundList: MutableList<BgColorInterval> = mutableListOf()
    private var lineUnderlineList: MutableList<UnderlineInterval> = mutableListOf()
    private var coloredTextInterval = TextColorInterval(0, 0, Color.parseColor("#8072FF"))

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        maxLines = maxLineCount
    }

    override fun draw(canvas: Canvas?) {
        if (layout == null) {
            super.draw(canvas)
            return
        }
        val lineCount = maxLineCount.coerceAtMost(layout.lineCount)
        for (i in 0 until lineCount) {
            val lineStart = layout.getLineStart(i)
            val lineEnd = layout.getLineEnd(i)
            drawBackgroundIfNeeded(i, lineStart, lineEnd, canvas!!)
            drawUnderlineIfNeeded(i, lineStart, lineEnd, canvas)
            drawText(i, lineStart, lineEnd, canvas)
        }
    }

    fun setColoredTextInterval(start: Int, end: Int) {
        coloredTextInterval = TextColorInterval(start, end, Color.parseColor("#8072FF"))
        invalidate()
    }

    fun setUnderLine(start: Int, end: Int) {
        lineUnderlineList.add(UnderlineInterval(start, end, Color.parseColor("#FFFA5151")))
        invalidate()
    }

    fun clearUnderLine() {
        lineUnderlineList.clear()
        invalidate()
    }

    fun setLineBackground(start: Int, end: Int) {
        lineBackgroundList.add(BgColorInterval(start, end, Color.parseColor("#4DFA5151")))
        invalidate()
    }

    fun clearLineBackground() {
        lineBackgroundList.clear()
        invalidate()
    }

    private fun drawBackgroundIfNeeded(line: Int, start: Int, end: Int, canvas: Canvas) {
        // Check if this line part of background span
        lineBackgroundList.forEach { lineBackground ->
            if (lineBackground.start < end && lineBackground.end > start) {
                val lineStartPos = if (lineBackground.start > start) {
                    layout.getPrimaryHorizontal(
                        lineBackground.start
                    )
                } else {
                    0f
                }
                val lineEndPos = if (lineBackground.end < end) {
                    layout.getPrimaryHorizontal(
                        lineBackground.end
                    )
                } else {
                    layout.getPrimaryHorizontal(
                        end - 1
                    )
                }
                val top = layout.getLineTop(line).toFloat()
                val bottom = layout.getLineBottom(line).toFloat()

                // Draw background
                mBackgroundPaint.color = lineBackground.color
                canvas.drawRect(lineStartPos, top, lineEndPos, bottom, mBackgroundPaint)
            }
        }
    }

    private fun drawUnderlineIfNeeded(line: Int, start: Int, end: Int, canvas: Canvas) {
        // Check if this line part of underline span
        lineUnderlineList.forEach { lineUnderline ->
            if (lineUnderline.start < end && lineUnderline.end > start) {
                val lineStartPos = if (lineUnderline.start > start) {
                    layout.getPrimaryHorizontal(
                        lineUnderline.start
                    )
                } else {
                    0f
                }
                val lineEndPos = if (lineUnderline.end < end) {
                    layout.getPrimaryHorizontal(
                        lineUnderline.end
                    )
                } else {
                    layout.getPrimaryHorizontal(
                        end - 1
                    )
                }
                val bottom = layout.getLineBottom(line).toFloat()

                // Wavy line interval
                val interval = 3 * paint.textSize / 12

                // Draw wavy line
                underlinePaint.color = lineUnderline.color
                underlinePaint.strokeWidth = 4f
                underlinePaint.isAntiAlias = true
                underlinePaint.style = Paint.Style.STROKE
                val wavePath = Path()
                wavePath.moveTo(lineStartPos, bottom)
                var dx = lineStartPos
                while (dx < lineEndPos) {
                    wavePath.rQuadTo(interval / 2, interval / 2, interval, 0f)
                    wavePath.rQuadTo(interval / 2, -interval / 2, interval, 0f)
                    dx += 2 * interval
                }
                canvas.drawPath(wavePath, underlinePaint)
            }
        }
    }

    private fun drawText(line: Int, start: Int, end: Int, canvas: Canvas) {
        val lineBottom = layout.getLineBottom(line).toFloat()
        val bottomWithoutDescent = lineBottom - layout.paint.descent()
        val x = layout.getPrimaryHorizontal(start)
        paint.style = Paint.Style.FILL
        // Check if this line part of color span
        if (coloredTextInterval.start <= end && coloredTextInterval.end >= start) {
            val subStart = kotlin.math.max(coloredTextInterval.start, start) - start
            val subEnd = kotlin.math.min(coloredTextInterval.end, end) - start
            val normalStr1 = text.subSequence(start, start + subStart).toString()
            val coloredStr = text.subSequence(start + subStart, start + subEnd).toString()
            val normalStr2 = text.subSequence(start + subEnd, end).toString()
            val x1 = x
            val x2 = x1 + paint.measureText(normalStr1)
            val x3 = x2 + paint.measureText(coloredStr)
            paint.color = currentTextColor
            canvas.drawText(normalStr1, 0, normalStr1.length, x1, bottomWithoutDescent, paint)
            paint.color = coloredTextInterval.color
            canvas.drawText(coloredStr, 0, coloredStr.length, x2, bottomWithoutDescent, paint)
            paint.color = currentTextColor
            canvas.drawText(normalStr2, 0, normalStr2.length, x3, bottomWithoutDescent, paint)
        } else {
            paint.color = currentTextColor
            canvas.drawText(text, start, end, x, bottomWithoutDescent, paint)
        }
    }

    private data class TextColorInterval(val start: Int, val end: Int, val color: Int)

    private data class BgColorInterval(val start: Int, val end: Int, val color: Int)

    private data class UnderlineInterval(val start: Int, val end: Int, val color: Int)
}
