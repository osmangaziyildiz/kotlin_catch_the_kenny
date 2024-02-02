package com.example.catchthekenny

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.catchthekenny.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score: Int = 0
    var imageList = ArrayList<ImageView>()
    var myRunnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageList.add(binding.imageView1)
        imageList.add(binding.imageView2)
        imageList.add(binding.imageView3)
        imageList.add(binding.imageView4)
        imageList.add(binding.imageView5)
        imageList.add(binding.imageView6)
        imageList.add(binding.imageView7)
        imageList.add(binding.imageView8)
        imageList.add(binding.imageView9)
        imageList.add(binding.imageView10)
        imageList.add(binding.imageView11)
        imageList.add(binding.imageView12)

        hideImages()

        object : CountDownTimer(15000, 1000) {
            override fun onTick(p0: Long) {
                binding.timerTextView.text = "Timer: ${p0 / 1000}"
            }

            override fun onFinish() {
                binding.timerTextView.text = "Time End"
                handler.removeCallbacks(myRunnable)
                for (images in imageList) {
                    images.visibility = View.INVISIBLE
                }
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Restart The Game?")
                alertDialog.setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialogInterface, which ->
                        val intentFromMain = intent
                        finish()
                        startActivity(intentFromMain)
                    })
                alertDialog.setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_LONG).show()
                    })

                alertDialog.show()
            }
        }.start()
    }

    fun increaseScore(view: View) {
        score += 1
        binding.scoreTextView.text = "Score: $score"
    }

    fun hideImages() {
        myRunnable = object : Runnable {
            override fun run() {
                for (images in imageList) {
                    images.visibility = View.INVISIBLE
                }

                val randomIndex = Random.nextInt(0, 11)
                imageList[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(myRunnable, 500)
            }
        }
        handler.post(myRunnable)
    }
}