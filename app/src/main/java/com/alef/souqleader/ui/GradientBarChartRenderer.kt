import android.graphics.*
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler
import android.graphics.RectF

class GradientBarChartRenderer(
    private val barChart: BarChart,
    animator: ChartAnimator,
    private val viewPortHandler: ViewPortHandler,
    private val startColor: Int,
    private val endColor: Int
) : BarChartRenderer(barChart, animator, viewPortHandler) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        // loop over entries of this dataset
        val bufferRect = RectF()

         val paint = Paint(Paint.ANTI_ALIAS_FLAG)
         val path = Path()

        for (i in 0 until dataSet.entryCount) {
            val e = dataSet.getEntryForIndex(i) as? BarEntry ?: continue
            try {
                // getBarBounds fills rect with pixel bounds for the given entry
                barChart.getBarBounds(e, bufferRect)

                // skip bars that are outside viewport
                if (!viewPortHandler.isInBoundsLeft(bufferRect.right) ||
                    !viewPortHandler.isInBoundsRight(bufferRect.left) ||
                    !viewPortHandler.isInBoundsTop(bufferRect.bottom) ||
                    !viewPortHandler.isInBoundsBottom(bufferRect.top)
                ) {
                    continue
                }

                // create vertical gradient for this bar (top -> bottom)
                val shader = LinearGradient(
                    bufferRect.left,
                    bufferRect.top,
                    bufferRect.left,
                    bufferRect.bottom,
                    startColor,
                    endColor,
                    Shader.TileMode.CLAMP
                )
                paint.shader = shader
                val radius = 6f * barChart.resources.displayMetrics.density
                path.reset()

                path.addRoundRect(
                    bufferRect,
                    floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f),
                    Path.Direction.CW
                )

                c.drawPath(path, paint)


            } catch (ex: Exception) {
                // safety: some MPAndroidChart versions may throw if entry not found; ignore
            }
        }

        // remove shader after drawing so other paints aren't affected
        paint.shader = null
    }
}
