package com.alfanshter.jatimpark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ViewFlipper
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.startActivity

class Menu : AppCompatActivity() {
    var flipper : ViewFlipper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val images =
            intArrayOf(R.drawable.banner, R.drawable.bannerbaru, R.drawable.bannerdua )
        flipper = findViewById(R.id.slider_menu)
        for (i in images.indices) {
            fliverImages(images[i])
        }
        for (image in images) fliverImages(image)

        tracking.setOnClickListener {
            startActivity<Tracking>()
        }
    }
    fun fliverImages(images: Int) {
        val imageView = ImageView(this)
        imageView.setBackgroundResource(images)
        flipper?.addView(imageView)
        flipper?.flipInterval = 2000
        flipper?.isAutoStart = true
        flipper?.setInAnimation(this, android.R.anim.slide_in_left)
        flipper?.setOutAnimation(this, android.R.anim.slide_out_right)
    }



}
