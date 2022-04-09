package com.example.feed

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.lifecycleScope
import com.example.feed.db.RoomEntity
import com.example.feed.db.RoomSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var amountTaps = 0
    private lateinit var textView: TextView
    private lateinit var image: ImageView
    private lateinit var mDb: RoomSingleton
    private lateinit var shareButton: ImageView
    private var id: Long = -1
    lateinit var xAnimation: SpringAnimation
    lateinit var yAnimation: SpringAnimation

    private companion object {
        val STIFFNESS = SpringForce.STIFFNESS_MEDIUM
        val DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        id = intent.getLongExtra(Login.USER_ID_ARG, -1)
        println("$id")
        shareButton = findViewById(R.id.share)
        shareButton.setOnClickListener {
            val intent = Intent(ACTION_SEND)
            intent.apply {
                setType("text/plane")
//                putExtra(Intent.EXTRA_SUBJECT, "qqq")
                putExtra(Intent.EXTRA_TEXT, "Satety: $amountTaps")
            }
            startActivity(intent)
        }

        mDb = RoomSingleton.getInstance(applicationContext)



        textView = findViewById(R.id.textView_number_taps)
        image = findViewById(R.id.imageViewCat)
        image.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                xAnimation = createSpringAnimation(
                    image, SpringAnimation.X, image.x, STIFFNESS, DAMPING_RATIO
                )
                yAnimation = createSpringAnimation(
                    image, SpringAnimation.Y, image.y, STIFFNESS, DAMPING_RATIO
                )
                image.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    fun countTaps(view: View) {
        amountTaps++
        textView.text = "$amountTaps"
        println(amountTaps)

        when (amountTaps % 15) {
            0 -> {
                val dX = 100f
                image.animate().x(dX).setDuration(0).start()
                xAnimation.start()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun saveResult(view: View) {
        val format = SimpleDateFormat("dd.MM.yyyy, hh:mm:ss")
        println("start")
        val roomEntity = RoomEntity(
            id_owner = id,
            date = format.format(Calendar.getInstance().getTime()),
            amount = amountTaps
        )
        println("end")
        lifecycleScope.launch(Dispatchers.IO) {
            mDb.roomDao().insert(roomEntity)
            println("1")
        }
    }

    fun goTable(view: View) {
        val intent = Intent(this, Table::class.java)
        intent.putExtra(Login.USER_ID_ARG, id)
        startActivity(intent)
    }

    fun createSpringAnimation(
        view: View,
        property: DynamicAnimation.ViewProperty,
        finalPosition: Float,
        stiffness: Float,
        dampingRatio: Float
    ): SpringAnimation {
        val animation = SpringAnimation(view, property)
        val spring = SpringForce(finalPosition)
        spring.stiffness = stiffness
        spring.dampingRatio = dampingRatio
        animation.spring = spring
        return animation
    }
}