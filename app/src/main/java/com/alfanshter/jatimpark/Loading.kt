package com.alfanshter.jatimpark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import com.alfanshter.jatimpark.auth.Login
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.startActivity

class Loading : AppCompatActivity() {
    private var Value = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        //Handler
        //Handler
        val handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                persentase.setText(Value.toString() + "%")
                if (Value == loading.getMax()) {
                    Toast.makeText(applicationContext, "Loading Selesai", Toast.LENGTH_SHORT)
                        .show()
                    startActivity<Login>()
                    finish()
                }
                Value++
            }
        }

        //thread
        //thread
        val thread = Thread(Runnable {
            try {
                for (w in 0..loading.getMax()) {
                    loading.setProgress(w)
                    handler.sendMessage(handler.obtainMessage())
                    Thread.sleep(20)
                }
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
        })
        thread.start()
    }
}
