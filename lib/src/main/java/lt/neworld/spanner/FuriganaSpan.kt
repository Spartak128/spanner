package lt.neworld.spanner

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

/**
 * Span that draws given reading (furigana) above base text.
 *
 * @param reading text to draw above
 * @param relativeSize size of furigana relative to base text size
 */
class FuriganaSpan @JvmOverloads constructor(
    private val reading: String,
    private val relativeSize: Float = DEFAULT_RELATIVE_SIZE
) : ReplacementSpan() {

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        val base = text.subSequence(start, end).toString()
        val width = paint.measureText(base).toInt()

        if (fm != null) {
            val baseFm = paint.fontMetricsInt
            val rubyPaint = Paint(paint)
            rubyPaint.textSize = paint.textSize * relativeSize
            val rubyFm = rubyPaint.fontMetricsInt
            val extra = rubyFm.bottom - rubyFm.top
            fm.ascent = baseFm.ascent - extra
            fm.top = baseFm.top - extra
            fm.descent = baseFm.descent
            fm.bottom = baseFm.bottom
        }

        return width
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val base = text.subSequence(start, end).toString()
        // draw base text
        canvas.drawText(base, x, y.toFloat(), paint)

        val rubyPaint = Paint(paint)
        rubyPaint.textSize = paint.textSize * relativeSize

        val baseWidth = paint.measureText(base)
        val rubyWidth = rubyPaint.measureText(reading)
        val rubyX = x + (baseWidth - rubyWidth) / 2f

        val baseTop = y + paint.fontMetricsInt.ascent
        val rubyBaseline = baseTop - rubyPaint.fontMetricsInt.descent

        canvas.drawText(reading, rubyX, rubyBaseline.toFloat(), rubyPaint)
    }

    companion object {
        const val DEFAULT_RELATIVE_SIZE = 0.5f
    }
}

