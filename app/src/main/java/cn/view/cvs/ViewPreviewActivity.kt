package cn.view.cvs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.view.cvs.view.LinearGradientProgressBar

class ViewPreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_preview)


        previewLinearGradientProgressBar()
    }

    private fun previewLinearGradientProgressBar() {
        val linearGradientProgressBar =
            findViewById<LinearGradientProgressBar>(R.id.linearGradientProgressBar)
        linearGradientProgressBar.setOnProgressChangeListener(object :
            LinearGradientProgressBar.ProgressChange {
            override fun onProgressChange(progress: Float) {
                println("进度条进度：$progress")
            }
        })
    }
}