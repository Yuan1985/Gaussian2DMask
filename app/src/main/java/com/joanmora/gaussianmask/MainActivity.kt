package com.joanmora.gaussianmask


import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.joanmora.gaussianmask.utils.GaussianMask


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    var size:Byte = 5
    var sigma = 1f

    val seekBar: SeekBar by lazy {findViewById<SeekBar>(R.id.seekBar)}
    val seekBarText: TextView by lazy {findViewById<TextView>(R.id.seekBarText)}
    val seekBar2: SeekBar by lazy {findViewById<SeekBar>(R.id.seekBar2)}
    val seekBarText2: TextView by lazy {findViewById<TextView>(R.id.seekBarText2)}
    val theview:ImageView by lazy {findViewById<ImageView>(R.id.theimageview)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.setOnSeekBarChangeListener(this)
        seekBar2.setOnSeekBarChangeListener(this)
        seekBar.max = 100
        seekBar.min = 1
        seekBar2.max = 1000
        seekBar2.min = 0
    }

    fun showMask(size: Byte, sigma: Float) {
        val bitmap = Bitmap.createBitmap((size * 2) + 1, (size * 2) + 1, Bitmap.Config.RGB_565)
        val mask = GaussianMask(size, sigma)
        val maskValues = mask.publicgetGaussianWeightedMask()
        val max = mask.getMaxValue(maskValues)
        val min = mask.getMinValue(maskValues)
        for (x in maskValues.indices) {
            val actual = maskValues[x]
            for (y in actual.indices) {
                val currentColor = map(maskValues[x][y], min, max, 0f, 1f)
                bitmap.setPixel(x, y, Color.rgb( currentColor, currentColor, currentColor))
                Log.w("YUAN", "x = $x , y = $y, value = ${maskValues[x][y]} , currentcolor = $currentColor")
            }
        }
        val bmpScaled = Bitmap.createScaledBitmap(bitmap, 400, 400, false)
        theview.setImageBitmap(bmpScaled)
    }

    fun map(
        value: Float,
        istart: Float,
        istop: Float,
        ostart: Float,
        ostop: Float
    ): Float {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart))
    }

    fun newSizeValue(size:Byte) {
        seekBarText.text = "Size: ${size} // Face length = ${((size*2)+1)}"
    }

    fun newSigmaValue(sigma:Float) {
        seekBarText2.text = "Sigma: $sigma"
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        seekBar?. let {
            if (this.seekBar === it) {
                Log.w("WWWWWWWWWWWWWWWWWWWWWWWW -> ","primer tocat")
                size = progress.toByte()
                newSizeValue(size)
            }
            if (this.seekBar2 === it) {
                Log.w("WWWWWWWWWWWWWWWWWWWWWWWW -> ","segon tocat")
                sigma = progress.toFloat()/100
                newSigmaValue(sigma)
            }
        }
        showMask(size, sigma)
    }
    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}
