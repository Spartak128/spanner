package lt.neworld.spanner

/**
 * Builder for [FuriganaSpan].
 */
internal class FuriganaSpanBuilder @JvmOverloads constructor(
    private val reading: String,
    private val relativeSize: Float = FuriganaSpan.DEFAULT_RELATIVE_SIZE
) : SpanBuilder {
    override fun build(): Any = FuriganaSpan(reading, relativeSize)
}

