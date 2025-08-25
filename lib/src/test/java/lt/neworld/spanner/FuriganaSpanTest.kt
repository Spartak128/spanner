package lt.neworld.spanner

import android.text.Spannable
import lt.neworld.spanner.Spans.furigana
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FuriganaSpanTest {

    @Test
    fun append_furigana() {
        val spanner = Spanner().append("漢字", furigana("かんじ"))
        val spans = spanner.getSpans(0, spanner.length, FuriganaSpan::class.java)
        assertEquals(1, spans.size)
        assertEquals("<FuriganaSpan>漢字</FuriganaSpan>", spanner.debugSpans())
    }

    private fun Spannable.debugSpans(): String {
        val all = getSpans()
        val debugText = StringBuilder(toString())

        var delta = 0

        fun insert(index: Int, text: CharSequence) {
            debugText.insert(index + delta, text)
            delta += text.length
        }

        all.forEach {
            val start = getSpanStart(it)
            val end = getSpanEnd(it)
            val name = it.javaClass.simpleName

            insert(start, "<$name>")
            insert(end, "</$name>")
        }

        return debugText.toString()
    }

    private fun Spannable.getSpans() = getSpans(0, length - 1)

    private fun Spannable.getSpans(start: Int, end: Int) = getSpans(start, end, Any::class.java)!!
}

