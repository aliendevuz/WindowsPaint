package uz.alien_dev.windowsonandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import uz.alien_dev.windowsonandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val context = this
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainDisplay: ConstraintLayout
    private lateinit var mainBackground: ImageView
    private lateinit var window: LinearLayout
    private lateinit var surfaceOfHeader: LinearLayout
    private lateinit var textOfHeader: TextView
    private lateinit var spaceOfHeader: LinearLayout
    private lateinit var closeButtonOfHeader: LinearLayout
    private lateinit var surfaceOfDisplay: LinearLayout
    private lateinit var shapeOfDisplay: CardView
    private lateinit var playground: Display

    private val width = 640
    private val height = 720
    private val halfWidth = width / 2
    private val halfHeight = height / 2

    private var dX = 0f
    private var dY = 0f
    private var lastX = 0f
    private var lastY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun init() {
        mainDisplay = binding.mainDisplay
        mainBackground = binding.mainBackground
        window = binding.window
        surfaceOfHeader = binding.surfaceOfHeader
        textOfHeader = binding.textOfHeader
        spaceOfHeader = binding.spaceOfHeader
        closeButtonOfHeader = binding.closeButtonOfHeader
        surfaceOfDisplay = binding.surfaceOfDisplay
        shapeOfDisplay = binding.shapeOfDisplay
        playground = binding.playground

        playground.layoutParams.width = width
        playground.layoutParams.height = height

        textOfHeader.setOnTouchListener { _, event -> windowMove(event) }
        spaceOfHeader.setOnTouchListener { _, event -> windowMove(event) }

        closeButtonOfHeader.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            window.startAnimation(animation)
            Handler().postDelayed({
                window.alpha = 0f
            }, 200L)
            Handler().postDelayed({
                finish()
            }, 600L)
        }
    }

    override fun onPause() {
        super.onPause()
        findViewById<Display>(R.id.playground).clear()
    }

    @SuppressLint("SetTextI18n")
    private fun windowMove(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                if (lastX == 0f)
                    lastX = window.x
                if (lastY == 0f)
                    lastY = window.y
                dX = window.x - event.rawX
                dY = window.y - event.rawY

            }
            MotionEvent.ACTION_MOVE -> {
                window.translationX = event.rawX + dX - lastX
                window.translationY = event.rawY + dY - lastY
            }
        }
        return true
    }
}